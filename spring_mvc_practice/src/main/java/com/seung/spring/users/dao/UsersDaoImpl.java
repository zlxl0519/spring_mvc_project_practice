package com.seung.spring.users.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersDao{
	@Autowired
	private SqlSession session;

	@Override
	public boolean isExist(String inputId) {
		String id=session.selectOne("users.isExist", inputId);
		if(id==null) {//존재하지 않는아이디
			return false;
		}else {//존재하는 아이디
			return true;
		}
		
	}
	
	
}
