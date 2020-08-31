<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>/views/users/signup_form.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script>
	var myApp=angular.module("myApp",[]);
	myApp.controller("formCtrl", function($scope, $http){
		$scope.checkId=function(){
			$http({
				url:"checkId.do",
				method:"get",
				params:{inputId:$scope.id}
			}).success(function(data){
				
			});
		}
	});
</script>
</head>
<body>
<div class="container" ng-controller="formCtrl">
	<h1>회원가입 폼</h1>
	<form action="signup.do" method="post" name="myForm" novalidate>
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" name="id" id="id"
				ng-model="id"
				ng-required="true"
				ng-pattern="/^[a-z].{4,10}/"
				ng-minlength="4"
				ng-maxlength="10"
				ng-class="{'is-invalid': ,'is-valid':}"
				ng-change="checkId()"/>
			<small class="form-text text-muted">영문자 소문자로 시작하고 4~10글자 이내로 입력하세요</small>
			<div class="invalid-feedback">
				<span ng-show="myForm.id.$error.change">이미 존재하는 아이디 입니다.</span>
				<span ng-show="myForm.id.$error.pattern">영문자 소문자로 시작하고 4~10글자 이내로 입력하세요</span>
			</div>
		</div>
		<div class="form-group">
			<label for="pwd">비밀번호</label>
			<input class="form-control" type="text" name="pwd" id="pwd" 
			ng-model="pwd"/>
		</div>
		<div class="form-group">
			<label for="pwd2">비밀번호 확인</label>
			<input class="form-control" type="text" name="pwd2" id="pwd2" 
			ng-model="pwd2"/>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="text" name="email" id="email" 
			ng-model="email"/>
		</div>
		<button class="btn btn-outline-primary" type="submit">가입</button>
		<button class="btn btn-outline-danger" type="reset">취소</button>
	</form>
</div>
</body>
</html>