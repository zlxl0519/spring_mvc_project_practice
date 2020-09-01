<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/users/updateform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<style>
	/* 프로필 업로드 폼을 화면에서 숨긴다.*/
	#profileForm{
		display:none;
	}
	/* 이미지를 작은 원형으로 만든다. */
	#profileImage{
		width : 50px;
		height : 50px;
		border : 1px solid #cecece;
		border-radius : 50%;
		cursor: pointer;
	}
</style>
</head>
<body>
<div class="container">
	<h1>회원정보 수정 폼</h1>
	<a href="javascript:" id="profileLink" >
		<c:choose>
			<c:when test="${empty dto.profile }"><%--empty는 빈문자도 true null도 true 로 인식해 준다. --%>
				<svg id="profileImage" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
		  			<path fill-rule="evenodd" d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
				</svg>
			</c:when>
			<c:otherwise>
				<img id="profileImage" src="${pageContext.request.contextPath }${dto.profile}" />
			</c:otherwise>
		</c:choose>
	</a>
	<form action="update.do" method="post">
		<input type="hidden" name="profile" id="profile" value="${dto.profile }" />
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" id="id" value="${dto.id }" disabled />
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="text" name="email" id="email" value="${dto.email }" />
		</div>
		<button class="btn btn-primary" type="submit">수정확인</button>
		<button class="btn btn-warning" type="reset">취소</button>
	</form>
	
	<form id="profileForm" action="profile_upload.do" method="post" enctype="multipart/form-data">
		<input id="image" type="file" name="image" accept=".jpg, .jpeg, .png, .JPG, .JPEG" />
	</form>
</div>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script>
	//1. 프로필 링크를 클릭했을때
	$("#profileLink").on("click", function(){
		//input type="file" 을 강제 클릭한다.
		$("#image").click();
	});
	
	//2. input type="file" 을 클릭해서 이미지를 선택하면
	$("#image").on("change", function(){
		$("#profileForm").submit();
	});
	
	//3. #profileForm ajaxForm 을 이용해서 제출
	$("#profileForm").ajaxForm(function(data){
		//기존 있던 profileImage를 없앤다.
		$("#profileImage").remove();
		//새로 img 요소를 만들어 준다 //data={imageSrc:"/upload/xxx.jpg"}
		$("<img/>").attr("id", "profileImage")
		.attr("src", "${pageContext.request.contextPath }"+data.imageSrc)
		.appendTo("#profileLink");
		//개인정보 수정 폼에 hidden profile 에 같이 전송되도록 한다.
		$("#profile").val(data.imageSrc);
	});
</script>
</body>
</html>