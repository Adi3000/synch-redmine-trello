package com.adi3000.code.synch.redminetrello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="VALUE_LABEL")
@SequenceGenerator(name = "value_label_id_seq", sequenceName = "value_label_id_seq", allocationSize=1)
public class ValueLabel {

	private Integer id;
	private String valueName;
	private String labelId;
	/**
	 * @return the id
	 */
	@Id
	@Column(name="VLL_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "value_label_id_seq")
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
	 * @return the value
	 */
	@Column(name="VALUE_NAME")
	public String getValueName() {
		return valueName;
	}
	/**
	 * @param value the value to set
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
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
