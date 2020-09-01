package com.seung.spring.users.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.seung.spring.users.dao.UsersDao;
import com.seung.spring.users.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDao usersDao;

	@Override
	public Map<String, Object> isExistId(String inputId) {
		//Dao에서 아이디 있는지 여부 찾아오기
		boolean isExist=usersDao.isExist(inputId);
		Map<String, Object> map=new HashMap<>();
		map.put("isExist", isExist);
		return map;
	}

	@Override
	public void addUser(UsersDto dto) {
		String inputPwd=dto.getPwd();
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPwd=encoder.encode(inputPwd);
		dto.setPwd(encodedPwd);
		//dao 에서 회원가입정보 넣기
		usersDao.insert(dto);
	}

	@Override
	public void loginProcess(UsersDto dto, HttpSession session, ModelAndView mView) {
		//dao 에서 아이디와 비밀번호에 유효한 정보를 얻어온다.
		UsersDto resultDto=usersDao.getData(dto.getId());
		boolean isValid=false;
		if(resultDto!=null) {//아이디는 일치하는 경우(아이디만 일치할수 있다.)
			String encodedPwd=resultDto.getPwd();
			String inputPwd=dto.getPwd();
			//DB 비밀번호와 input 비밀번호 비교
			isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		}
		if(isValid) {//만일 유효한 정보이면(있다면)
			session.setAttribute("id", dto.getId());
			mView.addObject("isSuccess", true);
		}else {
			mView.addObject("isSuccess", false);
		}
	}

	@Override
	public void getInfo(HttpSession session, ModelAndView mView) {
		//아이디로 정보 가져오기 dao
		String id=(String)session.getAttribute("id");
		UsersDto dto=usersDao.getData(id);
		mView.addObject("dto", dto);
	}

	@Override
	public void deleteUser(HttpSession session) {
		//session 에 있는 아이디를 읽어온다.
		String id=(String)session.getAttribute("id");
		//삭제 (dao 에서 삭제처리를한다.)
		usersDao.delete(id);
		//로그아웃 처리 (session이 필요하다)
		session.invalidate();
	}

	@Override
	public Map<String, Object> saveImageFile(HttpServletRequest request, MultipartFile mFile) {
		//원본 파일명
		String orgFileName=mFile.getOriginalFilename();
		
		//wepapp/ upload 파일 경로
		String realPath=request.getServletContext().getRealPath("/upload");
		//저장할 파일의 상세 경로// /upload/
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File upload=new File(filePath);
		if(!upload.exists()) {//만일 디렉토리가 존재하지 않으면
			upload.mkdir(); //디렉토리를 만든다.
		}
		//파일명 안겹치도록 저장할 파일명 구성
		String saveFileName= System.currentTimeMillis()+orgFileName;
		//업로드 폴더에 파일 저장
		try {
			mFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Map 에 json 형식을 저장하기
		Map<String, Object> map=new HashMap<>();
		map.put("imageSrc", "/upload/"+saveFileName);
		
		return map;
	}

	@Override
	public void updateUser(HttpSession session, UsersDto dto) {
		String id=(String)session.getAttribute("id");
		dto.setId(id);
		//dto 에 담긴정보를 가져가서 dao 에서 유저의 정보를 변경한다.
		usersDao.updateUser(dto);
	}

	@Override
	public void updatePwd(HttpSession session, ModelAndView mView, UsersDto dto) {
		String id=(String)session.getAttribute("id");
		dto.setId(id);
		//아이디에 해당되는 유저 정보 가져오기
		UsersDto resultDto=usersDao.getData(id);
		//작업 성공 여부
		boolean isSuccess=false;
		//1. 기존 비밀번호 입력한 것과 DB 암호화 비밀번호 비교
		String inputPwd=dto.getPwd();
		String encodedPwd=resultDto.getPwd();
		boolean isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		//2.만일 일치한다면 DB에 새 비밀번호를 암호화 해서 update, 성공여부
		if(isValid) {
			//새 비밀번호를 암호화
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			String encodednewPwd=encoder.encode(dto.getNewPwd());
			//새 비밀번호를 dto 에 다시 담기
			dto.setNewPwd(encodednewPwd);
			//DB에 비밀번호 반영하기
			usersDao.updatePwd(dto);
			//성공
			isSuccess=true;
		}
		mView.addObject("isSuccess", isSuccess);
	}
}
