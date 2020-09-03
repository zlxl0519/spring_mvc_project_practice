<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">
<title>/views/file/upload_form.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<h1>파일 업로드폼 입니다.</h1>
	<form action="upload.do" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<div class="form-group">
			<label for="title">제목(설명)</label>
			<input class="form-control" type="text" id="title" name="title" />
		</div>
		<div class="form-group">
			<label for="myFile">첨부파일</label>
			<input class="form-control" type="file" id="myFile" name="myFile" />
		</div>
		<button class="btn btn-primary" type="submit">업로드</button>
	</form>
</div>
</body>
</html>