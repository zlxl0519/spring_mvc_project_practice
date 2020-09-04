package com.seung.spring.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.seung.spring.file.dto.FileDto;
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
	@RequestMapping("/file/private/upload_form")
	public String uploadForm() {
		
		return "file/upload_form";
	}
	@RequestMapping("/file/private/upload")
	public ModelAndView upload(FileDto dto, ModelAndView mView, HttpServletRequest request) {
		//service 에서 fileDto 에 임시 저장해놓은 파일을 가져와서 파일이름, 사이즈, 저장파일명, 작성자를 만들고,
		//파일은 upload 폴더에 넣어주고,
		//dto 에 담아서 DB에 넣어준다. 
		//dto의 값을 저장해서 가져온다.
		fileService.saveFile(dto, mView, request);
		mView.setViewName("file/upload");
		return mView;
	}
	@RequestMapping("/rils/private/download")
	public ModelAndView download(@RequestParam int num, ModelAndView mView) {
		
		mView.setViewName("fileDownView");
		return mView;
	}
}
