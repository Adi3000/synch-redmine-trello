package com.adi3000.code.synch.redminetrello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="VERSION_LABEL")
@SequenceGenerator(name = "version_label_id_seq", sequenceName = "version_label_id_seq", allocationSize=1)
public class VersionLabel {

	private Integer id;
	private Integer versionId;
	private String labelId;
	/**
	 * @return the id
	 */
	@Id
	@Column(name="VL_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "version_label_id_seq")
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
	 * @return the labelId
	 */
	@Column(name="LABEL_ID")
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
