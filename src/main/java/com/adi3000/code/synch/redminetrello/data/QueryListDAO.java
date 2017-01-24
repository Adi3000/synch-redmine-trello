package com.adi3000.code.synch.redminetrello.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.adi3000.code.synch.redminetrello.model.QueryList;

@Repository
public class QueryListDAO extends AbstractDAO<QueryList>{

	@Value("#{'${redminetrello.queryList}'.split(',')}")
	private List<String> overridedQueryListString;
	private List<QueryList> queryLists;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initOverridedUserMember(){
		if(overridedQueryListString != null){
			queryLists = new ArrayList<QueryList>(overridedQueryListString.size());
			QueryList queryList = null;
			String[] queryListInfo = null;
			for(String quueryListString : overridedQueryListString){
				queryListInfo = quueryListString.split("_");
				queryList = new QueryList();
				queryList.setQueryId(Integer.valueOf(queryListInfo[0]));
				queryList.setListId(queryListInfo[1]);
				queryLists.add(queryList);
			}
		}else{
			queryLists = Collections.EMPTY_LIST;
		}
	}
	public List<QueryList> getQueryLists(){
		return queryLists;
	}
}
