package com.adi3000.code.synch.redminetrello.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.model.Card;

import com.adi3000.code.synch.redminetrello.model.IssueCard;
import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

@Service
public class SynchService {

	@Inject
	private IssueCardDAO issueCardDAO;
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
	}

	@Transactional
	public IssueCard getIssueCard(String cardId){
		return issueCardDAO.getIssueCardByCard(cardId);
	}
	@Transactional
	public IssueCard getIssueCard(Integer issueId){
		return issueCardDAO.getIssueCardByIssue(issueId);
	}
	public Card createCard(Issue issue, String idList) {
		Map<String, String> cardMap = new HashMap<String,String>();
		cardMap.put("name",issue.getSubject());
		cardMap.put("desc",String.format("%s\n%s/issues/%s", redmineUrl,issue.getId(), issue.getDescription()));
		Card card = trello.createCard(idList, issue.getSubject(), cardMap);
		return card;
	}

	public Card updateCard(Card card) {
		Map<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("id", card.getId());
		keyValueMap.put("idList", card.getIdList());
		keyValueMap.put("desc", card.getDesc());
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
			return redmine.getIssueManager().getIssues(projectId, queryId, null);
		} catch (RedmineException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}

}
