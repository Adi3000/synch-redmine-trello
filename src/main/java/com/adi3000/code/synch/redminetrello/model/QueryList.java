package com.adi3000.code.synch.redminetrello.model;

public class QueryList {

	private Integer id;
	private Integer queryId;
	private String listId;
	public QueryList() {}
	/**
	 * @return the queryId
	 */
	public Integer getQueryId() {
		return queryId;
	}
	/**
	 * @param queryId the queryId to set
	 */
	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}
	/**
	 * @return the listId
	 */
	public String getListId() {
		return listId;
	}
	/**
	 * @param listId the listId to set
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

}
