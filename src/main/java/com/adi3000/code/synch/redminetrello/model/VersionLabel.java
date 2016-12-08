package com.adi3000.code.synch.redminetrello.model;

public class VersionLabel {

	private Integer id;
	private Integer version;
	private String labelId;
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
	 * @return the labelId
	 */
	public String getLabelId() {
		return labelId;
	}
	/**
	 * @param labelId the labelId to set
	 */
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

}
