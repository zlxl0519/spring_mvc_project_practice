package com.seung.spring.users.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seung.spring.users.dao.UsersDao;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDao usersDao;

	@Override
	public Map<String, Object> isExistId(String inputId) {
		//Dao에서 아이디 있는지 여부 찾아오기
		boolean isExist=usersDao.isExist(inputId);
		Map<String, Object> map=new HashMap<>();
		map.put("isExist", isExist);
		return map;
	}
}
