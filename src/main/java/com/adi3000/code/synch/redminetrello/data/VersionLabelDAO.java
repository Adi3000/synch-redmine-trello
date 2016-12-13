package com.adi3000.code.synch.redminetrello.data;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.adi3000.code.synch.redminetrello.model.VersionLabel;

@Repository
public class VersionLabelDAO extends AbstractDAO<VersionLabel>{

	public VersionLabel getVersionLabelByVersion(Integer version){
		return (VersionLabel) createCriteria()
			.add(Restrictions.eq("versionId", version))
			.uniqueResult();
	}
}
