package com.adi3000.code.synch.redminetrello.data;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.adi3000.code.synch.redminetrello.model.IssueCard;

@Repository
public class IssueCardDAO extends AbstractDAO<IssueCard>{

	public IssueCard getIssueCardByCard(String cardId){
		return (IssueCard) createCriteria()
			.add(Restrictions.eq("cardId", cardId))
			.uniqueResult();
	}
	public IssueCard getIssueCardByIssue(Integer issueId){
		return (IssueCard) createCriteria()
				.add(Restrictions.eq("issueId", issueId))
				.uniqueResult();
	}
}
