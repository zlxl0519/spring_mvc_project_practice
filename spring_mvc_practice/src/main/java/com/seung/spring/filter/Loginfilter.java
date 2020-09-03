package com.seung.spring.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//web.xml 에 필터 정의와 필터 맵핑을 어노테이션을 이용해서 할수 있다
@WebFilter({"/users/private/*","/file/private/*"})// 로그인 했을때만 사용할수 있는것을 넣어놓는다.//여러곳에 적용할때는 배열로 적용할수 있다.
public class Loginfilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//클라리언트가 로그인 했는지 여부를 확인한다.
		//부모 type 을 자식 type 으로 casting 후
		HttpServletRequest req=(HttpServletRequest)request;
		//HttpSession 객체의 참조값을 얻어낸다.
		HttpSession session=req.getSession();
		//로그인된 아이디가 있는지 얻어와본다.
		String id=(String)session.getAttribute("id");
		if(id!=null) {//로그인된 상태
			//요청의 흐름 계속 진행 시키기
			chain.doFilter(request, response);
		}else {//로그인이 안된 상태
			/*
			 * 로그인 페이지로 강제로 리다일렉트 했다면 로그인성공후에 원래 가려던
			 * 목적지로 다시 보내야 하고
			 * GET 방식 전송되는 파라미터가 있다면 파라미터 정보도 같이 가지고
			 * 다녀야 한다. 
			 * - 쿼리스트링 : 파라미터 정보도 같이 가지고 다니는것
			 */
			//원래 가려던 url 정보 읽어오기
			String url=req.getRequestURI();
			//GET 방식 전송 파라미터를 query string 으로  얻어오기
			String query=req.getQueryString();
			//인코딩을 한다. 
			String encodedUrl=null;
			if(query==null) {//전송 파라미터가 없다면
				encodedUrl=URLEncoder.encode(url);
			}else {
				encodedUrl=URLEncoder.encode(url+"?"+query);
			}
			//로그인 폼으로 리다일렉트 이동하라고 응답
			HttpServletResponse res=(HttpServletResponse)response;
			String cPath=req.getContextPath();
			res.sendRedirect(cPath+"/users/loginform.do?url="+encodedUrl);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
