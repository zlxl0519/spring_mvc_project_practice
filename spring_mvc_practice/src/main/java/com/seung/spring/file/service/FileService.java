package com.seung.spring.file.service;

import javax.servlet.http.HttpServletRequest;

public interface FileService {
	public void getList(HttpServletRequest request);
	public void delete(int num, HttpServletRequest request);
}
