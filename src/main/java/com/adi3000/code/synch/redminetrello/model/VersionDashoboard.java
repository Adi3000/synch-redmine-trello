package com.adi3000.code.synch.redminetrello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="VERSION_DASHBOARD")
@SequenceGenerator(name = "version_dashboard_id_seq", sequenceName = "version_dashboard_id_seq", allocationSize=1)
public class VersionDashoboard {

	private Integer id;
	private Integer versionId;
	private String dashboardId;
	/**
	 * @return the id
	 */
	@Id
	@Column(name="VD_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "version_dashboard_id_seq")
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
	 * @return the version
	 */
	@Column(name="VERSION_ID")
	public Integer getVersionId() {
		return versionId;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	/**
	 * @return the dashboardId
	 */
	@Column(name="DASHBOARD_ID")
	public String getDashboardId() {
		return dashboardId;
	}
	/**
	 * @param dashboardId the dashboardId to set
	 */
	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

}
