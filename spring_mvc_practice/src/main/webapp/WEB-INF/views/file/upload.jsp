<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/file/upload.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<h1>알림</h1>
	<p class="alert alert-success">
		<strong>${id }</strong> 님이 업로드한 파일
		<strong>${dto.orgFileName }</strong> 이 성공적으로 저장되었습니다.
		파일의 크기는 <strong>${dto.fileSize }</strong> byte 입니다.
		<a class="alert-link" href="${pageContext.request.contextPath }/file/list.do">목록보기</a>
	</p>
</div>
</body>
</html>