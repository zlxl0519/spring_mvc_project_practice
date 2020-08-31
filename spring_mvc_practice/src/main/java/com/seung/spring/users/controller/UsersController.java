package com.seung.spring.users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
