package com.adi3000.code.synch.redminetrello.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.model.Card;
import org.trello4j.model.Label;

import com.adi3000.code.synch.redminetrello.model.IssueCard;
import com.adi3000.code.synch.redminetrello.model.VersionLabel;
import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Version;

@org.springframework.stereotype.Service
public class SynchService {

	@Inject
	private IssueCardDAO issueCardDAO;
	@Inject
	private VersionLabelDAO versionLabelDAO;
	@Value("${redmine.url}")
	private String redmineUrl;
	@Value("${redmine.apiKey}")
	private String redmineApiAccessKey;
	@Value("${trello.apiKey}")
	private String trelloApiAccessKey;
	@Value("${trello.token}")
	private String trelloToken;

	private Trello trello;
	private RedmineManager redmine;

	@PostConstruct
	public void initConnections(){
		trello = new TrelloImpl(trelloApiAccessKey, trelloToken);
		redmine = RedmineManagerFactory.createWithApiKey(redmineUrl, redmineApiAccessKey);
		redmine.setObjectsPerPage(1000);
	}

	@Transactional
	public IssueCard getIssueCard(String cardId){
		return issueCardDAO.getIssueCardByCard(cardId);
	}
	@Transactional
	public IssueCard getIssueCard(Integer issueId){
		return issueCardDAO.getIssueCardByIssue(issueId);
	}
	@Transactional
	public Card createCard(Issue issue, String idList, String idBoard) {
		Map<String, String> cardMap = new HashMap<String,String>();
		cardMap.put("name",String.format("[%d]%s", issue.getId(), issue.getSubject()));
		cardMap.put("desc",String.format("%s/issues/%s\n%s", redmineUrl,issue.getId(), issue.getDescription()));
		if(issue.getTargetVersion() != null){
			VersionLabel versionLabel = getVersionLabel(issue.getTargetVersion(), idBoard);
			cardMap.put("idLabels", versionLabel.getLabelId());
		}
		Card card = trello.createCard(idList, issue.getSubject(), cardMap);
		IssueCard issueCard = new IssueCard();
		issueCard.setCardId(card.getId());
		issueCard.setIssueId(issue.getId());
		issueCard.setLastRedmineUpdate(issue.getUpdatedOn());
		issueCardDAO.save(issueCard);
		return card;
	}

	private VersionLabel getVersionLabel(Version version, String boardId) {
		VersionLabel versionLabel = versionLabelDAO.getVersionLabelByVersion(version.getId());
		if(versionLabel == null){
			Label label = trello.createLabel(version.getName(), boardId, null);
			versionLabel = new VersionLabel();
			versionLabel.setLabelId(label.getId());
			versionLabel.setVersionId(version.getId());
			versionLabelDAO.save(versionLabel);
		}
		return versionLabel;
	}

	@Transactional
	public Card updateCard(Card card, Issue issue, String listId, String idBoard) {
		card.setIdList(listId);
		Map<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("id", card.getId());
		keyValueMap.put("name",String.format("[%d]%s", issue.getId(), issue.getSubject()));
		keyValueMap.put("idList", card.getIdList());
		keyValueMap.put("desc", card.getDesc());
		if(issue.getTargetVersion() != null){
			VersionLabel versionLabel = getVersionLabel(issue.getTargetVersion(), idBoard);
			keyValueMap.put("idLabels", versionLabel.getLabelId());
		}
		return trello.updateCard(card.getId(), keyValueMap);
	}
	public Card getCard(String cardId) {
		return trello.getCard(cardId);
	}
	public Issue getIssue(Integer issueId) {
		try {
			return redmine.getIssueManager().getIssueById(issueId, null);
		} catch (RedmineException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Issue> getIssuesFromQuery(String projectId, Integer queryId) {
		try {
			return redmine.getIssueManager().getIssues(projectId, queryId, Include.journals);
		} catch (RedmineException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}

}
