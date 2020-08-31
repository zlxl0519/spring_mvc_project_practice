package com.seung.spring.users.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
}
