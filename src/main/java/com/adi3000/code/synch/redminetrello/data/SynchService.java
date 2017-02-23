package com.adi3000.code.synch.redminetrello.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.model.Card;
import org.trello4j.model.Label;

import com.adi3000.code.synch.redminetrello.model.IssueCard;
import com.adi3000.code.synch.redminetrello.model.UserMember;
import com.adi3000.code.synch.redminetrello.model.ValueLabel;
import com.adi3000.code.synch.redminetrello.model.VersionLabel;
import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Version;

@org.springframework.stereotype.Service
public class SynchService {

	@Inject
	private IssueCardDAO issueCardDAO;
	@Inject
	private VersionLabelDAO versionLabelDAO;
	@Inject
	private ValueLabelDAO valueLabelDAO;
	@Value("${redmine.url}")
	private String redmineUrl;
	@Value("${redmine.apiKey}")
	private String redmineApiAccessKey;
	@Value("${trello.apiKey}")
	private String trelloApiAccessKey;
	@Value("${trello.token}")
	private String trelloToken;
	@Value("#{'${redminetrello.customFieldsIds}'.split(',')}")
	private List<String> customFieldsIds;
	@Value("${redminetrello.trelloDashboard}")
	private String dashboard;
	@Value("${redminetrello.redmineProject:@null}")
	private String project;
	@Inject
	private UserMemberDAO userMemberDAO;

	private Trello trello;
	private RedmineManager redmine;

	@PostConstruct
	public void initConnections(){
		trello = new TrelloImpl(trelloApiAccessKey, trelloToken);
		redmine = RedmineManagerFactory.createWithApiKey(redmineUrl, redmineApiAccessKey);
		redmine.setObjectsPerPage(1000);
	}

	public String getDashboard() {
		return dashboard;
	}

	public String getProject() {
		return project;
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
		String desc = String.format("%s/issues/%s\n%s", redmineUrl,issue.getId(), issue.getDescription());
		if(desc.length() >= 16384){
			desc = desc.substring(0, 16383);
		}
		cardMap.put("name",String.format("[%d]%s", issue.getId(), issue.getSubject()));
		cardMap.put("desc",desc);
		Set<String> idLabels = new HashSet<String>();
		ValueLabel valueLabel = null;
		//Affect version as label
		if(issue.getTargetVersion() != null){
			VersionLabel versionLabel = getVersionLabel(issue.getTargetVersion(), idBoard);
			idLabels.add(versionLabel.getLabelId());
		}
		//affect
		if(issue.getAssigneeId() != null){
			UserMember userMember=userMemberDAO.getUserMemberByUser(issue.getAssigneeId());
			if(userMember != null){
				cardMap.put("idMembers", userMember.getMemberId());
			}
		}
		// Adding label
		if(customFieldsIds != null){
			CustomField customField = null;
			for(String customFieldId : customFieldsIds){
				try{
					customField = issue.getCustomFieldById(Integer.valueOf(customFieldId).intValue());
					if(customField != null){
						valueLabel = getValueLabel(customField.getValue(),idBoard);
						idLabels.add(valueLabel.getLabelId());
					}
				}catch(NumberFormatException e){
					//TODO trace in debug the error
				}
			}
		}
		// Adding category as label
		if(issue.getCategory() != null){
			valueLabel = getValueLabel(issue.getCategory().getName(),idBoard);
			idLabels.add(valueLabel.getLabelId());
		}
		cardMap.put("idLabels", transformIdLabels(idLabels));
		//Submit and save all info
		Card card = trello.createCard(idList, issue.getSubject(), cardMap);
		IssueCard issueCard = new IssueCard();
		issueCard.setCardId(card.getId());
		issueCard.setIssueId(issue.getId());
		issueCard.setLastRedmineUpdate(issue.getUpdatedOn());
		issueCardDAO.save(issueCard);
		for(String idLabel : idLabels){
			trello.addLabelToCard(card.getId(), idLabel);
		}
		return trello.getCard(card.getId());
	}

	private String transformIdLabels(Collection<String> idLabels){
		if(idLabels == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(String id: idLabels){
			if(!first){
				sb.append(",");
			}
			first = false;
			sb.append(id);
		}
		return sb.toString();
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
	private ValueLabel getValueLabel(String value, String boardId) {
		ValueLabel valueLabel = valueLabelDAO.getValueLabelByValue(value);
		if(valueLabel == null){
			Label label = trello.createLabel(value, boardId, null);
			valueLabel = new ValueLabel();
			valueLabel.setLabelId(label.getId());
			valueLabel.setValueName(value);
			valueLabelDAO.save(valueLabel);
		}
		return valueLabel;
	}

	@Transactional
	public Card updateCard(Card card, Issue issue, String listId, String idBoard) {
		card.setIdList(listId);
		Map<String, String> keyValueMap = new HashMap<String, String>();
		Set<String> idLabels = new HashSet<String>();
		Set<String> existingLabels = new HashSet<String>();
		ValueLabel valueLabel = null;
		if(card.getLabels() != null){
			for (Label label : card.getLabels()){
				existingLabels.add(label.getId());
			}
		}
		keyValueMap.put("name",String.format("[%d]%s", issue.getId(), issue.getSubject()));
		keyValueMap.put("idList", card.getIdList());
		keyValueMap.put("desc", card.getDesc());
		if(issue.getTargetVersion() != null){
			VersionLabel versionLabel = getVersionLabel(issue.getTargetVersion(), idBoard);
			if(!existingLabels.contains(versionLabel.getLabelId())){
				idLabels.add(versionLabel.getLabelId());
			}
		}
		if(issue.getAssigneeId() != null){
			UserMember userMember=userMemberDAO.getUserMemberByUser(issue.getAssigneeId());
			if(userMember != null){
				keyValueMap.put("idMembers", userMember.getMemberId());
			}
		}
		// Adding label
		if(customFieldsIds != null){
			CustomField customField = null;
			for(String customFieldId : customFieldsIds){
				try{
					customField = issue.getCustomFieldById(Integer.valueOf(customFieldId).intValue());
					if(customField != null){
						valueLabel = getValueLabel(customField.getValue(),idBoard);
						if(!existingLabels.contains(valueLabel.getLabelId())){
							idLabels.add(valueLabel.getLabelId());
						}
					}
				}catch(NumberFormatException e){
				}
			}
		}
		// Adding category as label
		if(issue.getCategory() != null){
			valueLabel = getValueLabel(issue.getCategory().getName(),idBoard);
			if(!existingLabels.contains(valueLabel.getLabelId())){
				idLabels.add(valueLabel.getLabelId());
			}
		}
		for(String idLabel : idLabels){
			trello.addLabelToCard(card.getId(), idLabel);
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
