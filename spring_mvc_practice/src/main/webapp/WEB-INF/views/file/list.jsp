<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/file/list.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<h1>파일 목록입니다</h1>
	<table class="table table-hover table-sm">
		<thead>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>파일명</th>
			<th>파일크기</th>
			<th>등록일</th>
			<th>삭제</th>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${list }">
				<tr>
					<td>${tmp.num }</td>
					<td>${tmp.writer }</td>
					<td>${tmp.title }</td>
					<td><a href="download.do?${tmp.num }">${tmp.orgFileName }</a></td>
					<td><fmt:formatNumber value="${tmp.fileSize }" pattern="#,###"/>byte</td>
					<td>${tmp.regdate }</td>
					<td>
						<c:if test="${tmp.writer eq id }">
							<a href="private/delete.do?${tmp.num }">삭제</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="page-display">
		<ul class="pagination">
			<c:if test="${startPageNum ne 1 }">
				<li class="page-item">
					<a class="page-link" href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${keyword }" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
				<c:choose>
					<c:when test="${i eq pageNum }">
						<li class="page-item active"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition}&keyword=${keyword }">${i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition}&keyword=${keyword }">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount }">
				<li class="page-item">
	      			<a class="page-link" href="list.do?pageNum=${endPageNum+1 }&condition=${condition}&keyword="${keyword }" aria-label="Next">
	        			<span aria-hidden="true">&raquo;</span>
	      			</a>
	    		</li>
			</c:if>
		</ul>
	</div>
	<%-- hr 태그는 콘텐츠 내용에서 주제가 바뀔 때 사용할 수 있는 수평 가로선을 정의할 때 사용합니다. --%>
	<hr style="clear:left; margin-top:10px"/>
	<%-- 갖고 오는 목적이기 때문에 get 방식 , 제출하는 목적이면 post 방식 --%>
	<form action="list.do" method="get">
		<label for="condition">검색조건</label>
		<select name="condition" id="condition">
			<option value="title_filename" <c:if test="${condition eq 'title_filename' }">selected</c:if> >제목+파일명</option>
			<option value="title" <c:if test="${condition eq 'title' }">selected</c:if> >제목</option>
			<option value="writer" <c:if test="${condition eq 'writer' }">selected</c:if> >작성자</option>
		</select>
		<input type="text" value="${keyword }" name="keyword" placeholder="검색어..." />
		<button type="submit">검색</button>
	</form>
</div>
</body>
</html>