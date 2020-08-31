package com.seung.spring.users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seung.spring.users.dto.UsersDto;
import com.seung.spring.users.service.UsersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/users/signup_form")
	public String signupForm() {
		
		return "users/signup_form";
	}
	
	@RequestMapping("/users/checkId")
	@ResponseBody
	public Map<String, Object> checkId(@RequestParam String inputId){
		
		return usersService.isExistId(inputId);
	}
	
	@RequestMapping("/users/signup")
	public String signup(UsersDto dto) {
		//service 에서 dao 에 저장하는 비즈니스로직 작성한거 여기서 쓰기
		usersService.addUser(dto);
		return "users/signup";
	}
}
