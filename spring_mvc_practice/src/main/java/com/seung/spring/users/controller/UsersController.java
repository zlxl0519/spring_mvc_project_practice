package com.seung.spring.users.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping("/users/loginform")
	public String loginform(HttpServletRequest request) {
		//원래가려던 목적지가 있는지 읽어와 보기
		String url=request.getParameter("url");
		//가려던 목적지가 없다면
		if(url==null) {
			String cPath=request.getContextPath();
			url=cPath+"/home.do";//로그인후 인덱스 페이지로가기
		}
		request.setAttribute("url", url);
		return "users/loginform";
	}
	@RequestMapping("/users/login")
	public ModelAndView login(UsersDto dto, HttpServletRequest request, ModelAndView mView, 
			HttpSession session) {
		String url=request.getParameter("url");
		String encodedUrl=URLEncoder.encode("url");
		mView.addObject("url",url);
		mView.addObject("encodedUrl",encodedUrl);
		
		//service 에서 로그인 할 아이디 비밀번호에 맞는 정보 찾아오는 비즈니스로직 처리를 한다.
		usersService.loginProcess(dto, session, mView);
		mView.setViewName("users/login");
		return mView;
	}
	
}
