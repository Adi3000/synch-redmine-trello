package com.adi3000.code.synch.redminetrello.model;

public class VersionDashoboard {

	private Integer id;
	private Integer version;
	private String dashboardId;
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the dashboardId
	 */
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
