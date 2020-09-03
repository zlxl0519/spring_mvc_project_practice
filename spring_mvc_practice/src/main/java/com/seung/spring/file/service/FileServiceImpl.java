package com.seung.spring.file.service;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seung.spring.exception.NotDeleteException;
import com.seung.spring.file.dao.FileDao;
import com.seung.spring.file.dto.FileDto;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private FileDao fileDao;

	//한 페이지에 나타낼 row 의 갯수
	final int PAGE_ROW_COUNT=5;
	//하단 디스플레이 페이지 갯수
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public void getList(HttpServletRequest request) {
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		//어떤 것으로 검색했는지 파라미터 값
		String condition=request.getParameter("condition");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; //공차 수열 an=a1+(n-1)d
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		/*
		검색 키워드에 관련된 처리
		*/
		String keyword=request.getParameter("keyword");
		if(keyword==null){//전달된 키워드가 없다면
			condition="";
			keyword="";//빈 문자열을 넣어준다.
		}
		//인코딩된 키워드를 미리 만들어 둔다.
		String encodedK=URLEncoder.encode(keyword);
		//검색 키워드와 startRowNum, endRowNum 을 담을 FileDto 객체 생성
		FileDto dto=new FileDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){//만일 키워드가 넘어온다면
			if(condition.equals("title_filename")){
				//검색 키워드를 FileDto 객체의 필드에 담는다.
				dto.setTitle(keyword);
				dto.setOrgFileName(keyword);
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
		//파일 목록 얻어오기
		List<FileDto> list=fileDao.getList(dto);
		//전체 row 의 갯수를 담을 변수// 전체 row 의 갯수를 알아야지 페이지를 나눌수 있다.
		int totalRow=fileDao.getCount(dto);
		
		//전체 페이지의 갯수 구하기 //나눈것의 실수를 정수값으로 올림해서 사용한다는뜻 // 실수를 나오게하려면 정수 나누기 실수를 해야되서 나누는건 double로 우선 캐스팅
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);// totalPageCount=총 게시물 수 / 한화면에 나오는 페이지수
		//시작 페이지 번호 //1,2,3이든 page_display_count 가 5 이면 0
		/*(4/10)*10+1 = 0.4*10+1 = 4+1 = 5가 되겠지만, 
		 * 컴퓨터는 (int)(4/10)*10+1 = 0*10+1 = 0+1 = 1이 됩니다. 
		 * int 와 int 가 연산하면 int, 즉 정수로 계산되고 소수점은 버려집니다.
		 */ // 정수 정수끼리 나누면 정수이기때문
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		//EL 에서 사용할 값을 미리 request 에 담아두기
		request.setAttribute("list", list);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);
	}

	@Override
	public void delete(int num, HttpServletRequest request) {
		//삭제하려는 사람의 아이디와 작성자가 같지 않은데 지우려고 하면 예외 발생시키기
		//세션영역에서 아이디 가져오기
		String id=(String)request.getAttribute("id");
		//글에 대한 작성자의 대한 정보도 가져오기 (dao 이용)
		FileDto dto=fileDao.getData(num);
		String writer=dto.getWriter();
		if(!writer.equals(id)) {
			throw new NotDeleteException("남이 작성한것 지우지마세요!!");
		}
		//번호에 맞는 글을 dao 를 이용해서 삭제한다.
		fileDao.delete(num);
	}
	
}
