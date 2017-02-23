package com.adi3000.code.synch.redminetrello;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.trello4j.model.Card;

import com.adi3000.code.synch.redminetrello.data.QueryListDAO;
import com.adi3000.code.synch.redminetrello.data.SynchService;
import com.adi3000.code.synch.redminetrello.model.IssueCard;
import com.adi3000.code.synch.redminetrello.model.QueryList;
import com.taskadapter.redmineapi.bean.Issue;

public class SynchRedmineTrello {
	private static final int fileArgumentPosition = 0;

	public static void main(String[] args)  {
		String pathPropertyFile = args[fileArgumentPosition];
		System.setProperty("redminetrello.property.file", new File(pathPropertyFile).toURI().toString());
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		SynchService synchService = context.getBean(SynchService.class);
		QueryListDAO queryListDao = context.getBean(QueryListDAO.class);
		List<QueryList> queryLists = queryListDao.getQueryLists();

		//Analyze what is done before
		Collections.reverse(queryLists);

		List<Issue> issues = null;
		Card card = null;
		IssueCard issueCard =null;
		for(QueryList queryList: queryLists ){
			issues = synchService.getIssuesFromQuery(synchService.getProject(), queryList.getQueryId());
			for (Issue issue : issues) {
				try{
					issueCard = synchService.getIssueCard(issue.getId());
					if(issueCard == null){
						card = synchService.createCard(issue, queryList.getListId(), synchService.getDashboard());
					}else{
						card = synchService.getCard(issueCard.getCardId());
						synchService.updateCard(card, issue, queryList.getListId(), synchService.getDashboard());
					}
				}catch(Exception e){
					System.err.println(String.format("%s cannot be updated with issue %s", card, issue.getId()));
					e.printStackTrace();
				}
			}
		}
		((ConfigurableApplicationContext)context).close();
	}

}
