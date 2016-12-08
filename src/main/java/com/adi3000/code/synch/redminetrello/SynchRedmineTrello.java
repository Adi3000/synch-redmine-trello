package com.adi3000.code.synch.redminetrello;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.TrelloURL;
import org.trello4j.model.Action;
import org.trello4j.model.Card;

import com.adi3000.code.synch.redminetrello.data.SynchService;
import com.adi3000.code.synch.redminetrello.model.IssueCard;
import com.adi3000.code.synch.redminetrello.model.QueryList;
import com.adi3000.code.synch.redminetrello.model.VersionDashoboard;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Issue;

public class SynchRedmineTrello {
	private static final int fileArgumentPosition = 0;

	public static void main(String[] args)  {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		String pathPropertyFile = args[fileArgumentPosition];
		SynchService synchService = context.getBean(SynchService.class);
		System.setProperty("redminetrello.property.file", new File(pathPropertyFile).toURI().toString());
		
		String redmineApiAccessKey = context.getEnvironment().getProperty("redmine.apiKey");
		String uri = context.getEnvironment().getProperty("redmine.url");
		String trelloApiAccessKey =  context.getEnvironment().getProperty("trello.apiKey");
		String trelloToken = context.getEnvironment().getProperty("trello.token");
		String projectKey = "ofl";
		
		VersionDashoboard versionDashoboard = new VersionDashoboard();
		versionDashoboard.setVersion(143);
		versionDashoboard.setDashboardId("56e18e4451da4da4db599a0b");
		
		
		List<QueryList> queryLists = new ArrayList<QueryList>();
		QueryList queryListEntry = null;
		//Backlog
		queryListEntry = new QueryList();
		queryListEntry.setListId("56e18e4e2108edf8af2a0e96");
		queryListEntry.setQueryId(102);
		
		//TODO
		queryListEntry = new QueryList();
		queryListEntry.setListId("56e18e5091b2000bf40f69e3");
		queryListEntry.setQueryId(101);
		queryLists.add(queryListEntry);

		//In Progress
		queryListEntry = new QueryList();
		queryListEntry.setListId("56e18e543d37080d22cff2ac");
		queryListEntry.setQueryId(103);
		queryLists.add(queryListEntry);

		//Done
		queryListEntry = new QueryList();
		queryListEntry.setListId("56e18e556a64556ad2335157");
		queryListEntry.setQueryId(104);
		queryLists.add(queryListEntry);
		
		//Analyze what is done before
		Collections.reverse(queryLists);
		
		List<Issue> issues = null;
		Card card = null;
		IssueCard issueCard =null;
		List<Action> actions = null;
		for(QueryList queryList: queryLists ){
			try{
				issues = mgr.getIssueManager().getIssues(projectKey, queryList.getQueryId());
				for (Issue issue : issues) {
					issueCard = synchService.getIssueCard(issue.getId());
					if(issueCard == null){
						card = synchService.createCard(issue, queryList.getListId());
						actions = null;
					}else{
						trello.getCard(issueCard.getCardId());
						actions = trello.getActionsByCard(issueCard.getCardId());
						
					}
					issue.get
				}
			}catch(RedmineException e){
				e.printStackTrace();
			}
		}
	}

}
