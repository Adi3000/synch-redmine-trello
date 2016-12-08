package com.adi3000.code.synch.redminetrello.model;

import java.time.LocalDate;

public class IssueCard {

	private Integer id;
	private Integer issueId;
	private String cardId;
	private LocalDate lastTrelloAction;
	private LocalDate lastRedmineUpdate;
	
	public IssueCard() {}

	/**
	 * @return the issueId
	 */
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
	public LocalDate getLastTrelloAction() {
		return lastTrelloAction;
	}

	/**
	 * @param lastTrelloAction the lastTrelloAction to set
	 */
	public void setLastTrelloAction(LocalDate lastTrelloAction) {
		this.lastTrelloAction = lastTrelloAction;
	}

	/**
	 * @return the lastRedmineUpdate
	 */
	public LocalDate getLastRedmineUpdate() {
		return lastRedmineUpdate;
	}

	/**
	 * @param lastRedmineUpdate the lastRedmineUpdate to set
	 */
	public void setLastRedmineUpdate(LocalDate lastRedmineUpdate) {
		this.lastRedmineUpdate = lastRedmineUpdate;
	}

}
