<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<p>
		<strong>${param.id }</strong>님 탈퇴처리 되었습니다.
		<a href="${pageContext.request.contextPath }/home.do">확인</a>
	</p>
</div>
</body>
</html>