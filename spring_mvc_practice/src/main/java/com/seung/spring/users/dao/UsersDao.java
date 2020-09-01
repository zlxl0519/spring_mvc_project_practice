package com.seung.spring.users.dao;

import com.seung.spring.users.dto.UsersDto;

public interface UsersDao {
	public boolean isExist(String inputId);
	public void insert(UsersDto dto); //회원가입 유저 추가입력
	public UsersDto getData(String id); //아이디로 사용자 정보 다 가져오기
	public void delete(String id);
	public void updateUser(UsersDto dto);
	public void updatePwd(UsersDto dto);
}
