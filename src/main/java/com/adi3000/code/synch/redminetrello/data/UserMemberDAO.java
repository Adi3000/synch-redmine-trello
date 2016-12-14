package com.adi3000.code.synch.redminetrello.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.adi3000.code.synch.redminetrello.model.UserMember;

@Repository
public class UserMemberDAO extends AbstractDAO<UserMember>{

	@Value("#{'${redminetrello.members}'.split(',')}")
	private List<String> overridedMembersString;
	private Map<Integer, UserMember> userIdMap;
	private Map<String, UserMember> memberIdMap;

	@PostConstruct
	public void initOverridedUserMember(){
		userIdMap = new HashMap<Integer,UserMember>();
		memberIdMap = new HashMap<String,UserMember>();
		if(overridedMembersString != null){
			UserMember userMember = null;
			String[] userMemberInfo = null;
			for(String memberString : overridedMembersString){
				userMemberInfo = memberString.split("_");
				userMember = new UserMember();
				userMember.setUserId(Integer.valueOf(userMemberInfo[0]));
				userMember.setMemberId(userMemberInfo[1]);
				userIdMap.put(userMember.getUserId(), userMember);
				memberIdMap.put(userMember.getMemberId(), userMember);
			}
		}

	}
	public UserMember getUserMemberByMember(String memberId){
		if(memberIdMap.containsKey(memberId)){
			return memberIdMap.get(memberId);
		}else{
			return (UserMember) createCriteria()
					.add(Restrictions.eq("memberId", memberId))
					.uniqueResult();
		}
	}
	public UserMember getUserMemberByUser(Integer userId){
		if(userIdMap.containsKey(userId)){
			return userIdMap.get(userId);
		}else{
			return (UserMember) createCriteria()
					.add(Restrictions.eq("userId", userId))
					.uniqueResult();
		}
	}
}
