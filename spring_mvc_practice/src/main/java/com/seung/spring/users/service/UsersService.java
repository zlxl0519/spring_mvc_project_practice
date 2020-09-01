package com.seung.spring.users.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.seung.spring.users.dto.UsersDto;

public interface UsersService {
	public Map<String, Object> isExistId(String inputId);
	public void addUser(UsersDto dto);
	public void loginProcess(UsersDto dto, HttpSession session, ModelAndView mView);
	public void getInfo(HttpSession session, ModelAndView mView);
	public void deleteUser(HttpSession session);
}
