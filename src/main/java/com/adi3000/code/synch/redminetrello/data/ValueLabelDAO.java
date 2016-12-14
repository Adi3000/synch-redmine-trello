package com.adi3000.code.synch.redminetrello.data;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.adi3000.code.synch.redminetrello.model.ValueLabel;

@Repository
public class ValueLabelDAO extends AbstractDAO<ValueLabel>{

	public ValueLabel getValueLabelByValue(String value){
		return (ValueLabel) createCriteria()
			.add(Restrictions.eq("valueName", value))
			.uniqueResult();
	}
}
