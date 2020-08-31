package com.seung.spring.users.dao;

import com.seung.spring.users.dto.UsersDto;

public interface UsersDao {
	public boolean isExist(String inputId);
	public void insert(UsersDto dto); //회원가입 유저 추가입력
}
