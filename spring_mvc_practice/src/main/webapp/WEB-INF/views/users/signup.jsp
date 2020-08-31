<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/users/signup.jsp</title>
</head>
<body>
	<p>
		<strong>${param.id }</strong>님 가입되었습니다.
		<a href="${pageContext.request.contextPath }/users/loginform.do">로그인하러가기</a>
		<a href="${pageContext.request.contextPath }">홈으로 가기</a>
	</p>
</body>
</html>