package com.seung.spring.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.seung.spring.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/file/list")
	public ModelAndView list(ModelAndView mView, HttpServletRequest request) {
		//service 에서 리스트를 받아온다.
		fileService.getList(request);
		mView.setViewName("file/list");
		return mView;
	}
	
	@RequestMapping("/file/private/delete")
	public String delete(@RequestParam int num, HttpServletRequest request) {
		//service 에서 번호에 맞는 글을 삭제한다. 그리고 작성자와 아이디가 다른 사람이 글을 지우려고 하면 예외처리하기
		fileService.delete(num, request);
		return "redirect:/file/list.do";
	}
}
