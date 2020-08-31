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
		$scope.checkId=function(){//아이디가 DB에 있는지 여부 검사
			$http({
				url:"checkId.do",
				method:"get",
				params:{inputId:$scope.id}
			}).success(function(data){
				$scope.canUseId=!data.isExist;
				//DB에 존재하는아이디이면
				if(data.isExist){// data= {isExist: true} or {isExist: false}
					$scope.errorMessage="이미 존재하는 아이디 입니다";
				}
				myForm.id.$valid=!data.isExist;
				myForm.id.$invalid=data.isExist;
			});
		}
		
		$scope.checkPwd=function(){
			$scope.myForm.pwd.$valid=$scope.pwd==$scope.pwd2;
			$scope.myForm.pwd.$invalid=$scope.pwd!=$scope.pwd2;
		}
	});
</script>
</head>
<body>
<div class="container" ng-controller="formCtrl">
	<h1>회원가입 폼</h1>
	<p>아이디 사용가능 여부:{{canUseId}} </p>
	<form action="signup.do" method="post" name="myForm" novalidate>
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" name="id" id="id"
				ng-model="id"
				ng-required="true"
				ng-pattern="/^[a-z].{3,9}$/"
				ng-minlength="4"
				ng-maxlength="10"
				ng-class="{'is-invalid': (myForm.id.$invalid || !canUseId) && myForm.id.$dirty ,'is-valid':myForm.id.$valid}"
				ng-change="checkId()"/>
			<small class="form-text text-muted">영문자 소문자로 시작하고 4~10글자 이내로 입력하세요</small>
			<div class="invalid-feedback">
				<span ng-show="!canUseId">{{errorMessage}}</span>
				<span ng-show="myForm.id.$error.pattern">형식에 맞게 작성해주세요</span>
			</div>
		</div>
		<div class="form-group">
			<label for="pwd">비밀번호</label>
			<input class="form-control" type="password" name="pwd" id="pwd" 
				ng-model="pwd"
				ng-required="true"
				ng-minlength="5"
				ng-maxlength="10"
				ng-class="{'is-invalid':myForm.pwd.$invalid && myForm.pwd.$dirty ,'is-valid':myForm.pwd.$valid }"
				ng-change="checkPwd()"/>
			<small class="form-test test-muted">최소 5글자~10글자 이내 입력하세요</small>
			<div class="invalid-feedback">비밀번호 확인을 해주세요</div>
		</div>
		<div class="form-group">
			<label for="pwd2">비밀번호 확인</label>
			<input class="form-control" type="password" name="pwd2" id="pwd2" 
				ng-model="pwd2"
				ng-change="checkPwd()"/>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="text" name="email" id="email" 
				ng-model="email"
				ng-required="true"
				ng-pattern="/@/"
				ng-class="{'is-invalid':myForm.email.$invalid && myForm.email.$dirty,'is-valid':myForm.email.$valid}"/>
			<div class="invalid-feedback">이메일 형식에 맞게 입력해주세요</div>
		</div>
		<button class="btn btn-outline-primary" type="submit" ng-disabled="myForm.$invalid">가입</button>
		<button class="btn btn-outline-danger" type="reset">취소</button>
	</form>
</div>
</body>
</html>