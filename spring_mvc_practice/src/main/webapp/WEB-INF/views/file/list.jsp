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
</div>
</body>
</html>