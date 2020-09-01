<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>/views/users/pwd_updateform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>
<script>
	var myApp=angular.module("myApp",[]);
	myApp.controller("formCtrl",function($scope, $http){
		$scope.changedPwd=function(){
			$scope.isSamePwd=true;
			$scope.sameNewPwd=$scope.newPwd==$scope.newPwd2;
			if($scope.sameNewPwd){
				$scope.isSamePwd=false;
			}
		}
	});
</script>
</head>
<body>
<div class="container" ng-controller="formCtrl">
	<h1>비밀번호 변경 폼</h1>
	<p>새 비밀번호 일치 여부:{{isSamePwd}}</p>
	<form action="pwd_update.do" method="post" name="myForm" novalidate>
		<div class="form-group">
			<label for="pwd">기존 비밀번호</label>
			<input class="form-control" type="password" name="pwd" id="pwd"
				ng-model="pwd" />
		</div>
		<div class="form-group">
			<label for="newPwd">새 비밀번호</label>
			<input class="form-control" type="text" name="newPwd" id="newPwd" 
				ng-model="newPwd"
				ng-required="true"
				ng-minlength="5"
				ng-maxlength="10"
				ng-class="{'is-invalid':(myForm.newPwd.$invalid || isSamePwd) && myForm.newPwd.$dirty ,'is-valid':myForm.newPwd.$valid}"
				ng-change="changedPwd()"/>
			<small class="form-text text-muted">최소 5글자~10글자 이내로 입력 하세요.</small>
			<div class="invalid-feedback">
				<span ng-if="myForm.newPwd.$valid && isSamePwd">새로운 비밀번호가 일치하지 않습니다</span>
				<span ng-if="myForm.newPwd.$error.minlength || myForm.newPwd.$error.maxlength">글자수에 맞게 입력해주세요</span>
			</div>
		</div>
		<div class="form-group">
			<label for="newPwd2">새 비밀번호 확인</label>
			<input class="form-control" type="text" name="newPwd2" id="newPwd2"
				ng-model="newPwd2"
				ng-required="true"
				ng-minlength="5"
				ng-maglength="10"
				ng-class="{'is-invalid':(myForm.newPwd.$invalid || isSamePwd) && myForm.newPwd.$dirty ,'is-valid':myForm.newPwd.$valid}"
				ng-change="changedPwd()"/>
			<div class="invalid-feedback">
				<span ng-if="myForm.newPwd.$valid && isSamePwd">새로운 비밀번호가 일치하지 않습니다</span>
			</div>
		</div>
		<button class="btn btn-outline-primary" type="submit" ng-disabled="myForm.$invalid">수정하기</button>
	</form>
</div>
</body>
</html>