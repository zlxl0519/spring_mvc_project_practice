package com.seung.spring.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
