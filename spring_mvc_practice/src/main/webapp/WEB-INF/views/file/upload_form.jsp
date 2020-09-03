<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>/views/file/upload_form.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>
<script>
	var myApp=angular.module("myApp",[]);
	myApp.controller("formCtrl",function(){
		
	});
</script>
</head>
<body>
<div class="container" ng-controller="formCtrl">
	<h1>파일 업로드폼 입니다.</h1>
	<form action="upload.do" method="post" name="myForm">
		<div class="form-group">
			<label for="title">제목(설명)</label>
			<input class="form-control" type="text" id="title" name="title" />
		</div>
		<div class="form-group">
			<label for="upload_file">첨부파일</label>
			<input class="form-control" type="file" id="upload_file" name="upload_file" 
				/>
		</div>
		<button class="btn btn-primary" type="submit">업로드</button>
	</form>
</div>
</body>
</html>