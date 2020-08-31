package com.seung.spring.users.service;

import java.util.Map;

import com.seung.spring.users.dto.UsersDto;

public interface UsersService {
	public Map<String, Object> isExistId(String inputId);
	public void addUser(UsersDto dto);
}
