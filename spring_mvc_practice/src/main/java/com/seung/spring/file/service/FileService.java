package com.seung.spring.file.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.seung.spring.file.dto.FileDto;

public interface FileService {
	public void getList(HttpServletRequest request);
	public void delete(int num, HttpServletRequest request);
	public void saveFile(FileDto dto, ModelAndView mView, HttpServletRequest request);
}
