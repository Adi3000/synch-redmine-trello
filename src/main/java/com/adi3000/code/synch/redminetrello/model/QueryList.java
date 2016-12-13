package com.adi3000.code.synch.redminetrello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="QUERY_LIST")
@SequenceGenerator(name = "query_list_id_seq", sequenceName = "query_list_id_seq", allocationSize=1)
public class QueryList {

	private Integer id;
	private Integer queryId;
	private String listId;
	public QueryList() {}
	/**
	 * @return the id
	 */
	@Id
	@Column(name="QL_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_list_id_seq")
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
	 * @return the queryId
	 */
	@Column(name="QUERY_ID")
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
	@Column(name="LIST_ID")
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
