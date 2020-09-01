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
import org.springframework.web.multipart.MultipartFile;
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
	@RequestMapping("/users/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home.do";
	}
	@RequestMapping("/users/private/info")
	public ModelAndView info(ModelAndView mView, HttpServletRequest request) {
		//service 에서 아이디로 정보 가져와서 mView 에 저장하는 로직 
		usersService.getInfo(request.getSession(), mView);
		mView.setViewName("users/info");
		return mView;
	}
	@RequestMapping("/users/private/delete")
	public ModelAndView delete(HttpServletRequest request, ModelAndView mView) {
		//service에서 id 에 맞는 user의 정보를지운다.그리고 로그아웃처리를 한다.
		usersService.deleteUser(request.getSession());
		mView.setViewName("users/delete");
		return mView;
	}
	@RequestMapping("/users/private/updateform")
	public ModelAndView updateFrom(HttpServletRequest request, ModelAndView mView) {
		usersService.getInfo(request.getSession(), mView);
		mView.setViewName("users/updateform");
		return mView;
	}
	//ajax 프로필사진 업로드 요청 처리
	@RequestMapping("/users/private/profile_upload")
	@ResponseBody
	public Map<String, Object> profile_upload(HttpServletRequest request, @RequestParam MultipartFile image){
		//service 에서 upload 폴더에 이미지를 저장하고 map 을 리턴해온다.
		Map<String, Object> map=usersService.saveImageFile(request, image);
		//map을 리턴한다.
		return map;
	}
	@RequestMapping("/users/private/update")
	public String update(HttpServletRequest request, UsersDto dto) {
		//service 에서 session 아이디를 받아서 유저 정보를 업데이트 시킨다.
		usersService.updateUser(request.getSession(), dto);
		return "redirect:/users/private/info.do";
	}
	@RequestMapping("/users/private/pwd_updateform")
	public String pwdUpdateform() {
		return "users/pwd_updateform";
	}
	@RequestMapping("/users/private/pwd_update")
	public ModelAndView updatePwd(HttpServletRequest request, ModelAndView mView, UsersDto dto) {
		//Service에서 암호화된 비밀번호와 기존 비밀번호를 비교해서 맞으면 성공, 아니면 실패를반환
		usersService.updatePwd(request.getSession(), mView, dto);
		mView.setViewName("users/pwd_update");
		return mView;
	}
}
