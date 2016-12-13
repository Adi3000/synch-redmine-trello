package com.adi3000.code.synch.redminetrello.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ISSUE_CARD")
@SequenceGenerator(name = "issue_card_id_seq", sequenceName = "issue_card_id_seq", allocationSize=1)
public class IssueCard {

	private Integer id;
	private Integer issueId;
	private String cardId;
	private Date lastTrelloAction;
	private Date lastRedmineUpdate;

	public IssueCard() {}

	/**
	 * @return the id
	 */
	@Id
	@Column(name="IC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_card_id_seq")
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the issueId
	 */
	@Column(name="ISSUE_ID")
	public Integer getIssueId() {
		return issueId;
	}

	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	/**
	 * @return the cardId
	 */
	@Column(name="CARD_ID")
	public String getCardId() {
		return cardId;
	}

	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the lastTrelloAction
	 */
	@Column(name="LAST_TRELLO_ACTION")
	public Date  getLastTrelloAction() {
		return lastTrelloAction;
	}

	/**
	 * @param lastTrelloAction the lastTrelloAction to set
	 */
	public void setLastTrelloAction(Date lastTrelloAction) {
		this.lastTrelloAction = lastTrelloAction;
	}

	/**
	 * @return the lastRedmineUpdate
	 */
	@Column(name="LAST_REDMINE_UPDATE")
	public Date getLastRedmineUpdate() {
		return lastRedmineUpdate;
	}

	/**
	 * @param lastRedmineUpdate the lastRedmineUpdate to set
	 */
	public void setLastRedmineUpdate(Date lastRedmineUpdate) {
		this.lastRedmineUpdate = lastRedmineUpdate;
	}

}
