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
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>
</head>
<script>
	var myApp=angular.module("myApp",[]);
	myApp.controller("pagingCtrl", function($scope){
		var startPageNum=${startPageNum};
		var endPageNum=${endPageNum};
		$scope.pageNum='<c:out value="${pageNum}"/>';
		var totalPageCount=${totalPageCount};
		var list='<c:out value="${list}"/>';
		
		$scope.currentPage=0; 
		$scope.pageSize=5; //한페이지에 보여줄 목록수
		$scope.totalPages=function(){
			return Math.ceil($scope.data.length/$scope.pageSize);
		}
		$scope.data=[];
		for(var i=1; i<= totalPageCount; i++){
			$scope.data.push(i);
		}
	});
	myApp.filter('startFrom', function(){
		return function(input,start){
			start=+start;
			return input.slice(start);
		}
	});
</script>
<body>
<div ng-app="myApp" class="container">
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
	<%--anrularjs 이용 페이징 처리 --%>
	<div ng-controller="pagingCtrl" class="page-display">
		<button ng-hide="currentPage==0" ng-click="currentPage=currentPage-1">
			Previous
		</button>
		<ul class="pagination pagination-sm">
			<li class="page-item" ng-repeat="item in data | startFrom:currentPage*pageSize  | limitTo:pageSize">
				<a class="page-link" href="list.do?pageNum=${pageNum}&condition=${conditon }&keyword=${encodedK }">{{item}}</a>
			</li>
		</ul>
		<button ng-hide="currentPage >= data.length/pageSize-1" ng-click="currentPage=currentPage+1">
			Next
		</button>
	</div>
	
	<%-- hr 태그는 콘텐츠 내용에서 주제가 바뀔 때 사용할 수 있는 수평 가로선을 정의할 때 사용합니다. --%>
	<hr style="clear:left; margin-top:10px"/>
	<%-- 갖고 오는 목적이기 때문에 get 방식 , 제출하는 목적이면 post 방식 --%>
	<form action="list.do" method="get">
		<label for="condition">검색조건</label>
		<select name="condition" id="condition">
			<option value="title_filename" <c:if test="${condition eq 'title_filename' }">selected</c:if> >제목+파일명</option>
			<option value="title" <c:if test="${condition eq 'title' }">selected</c:if> >제목</option>
			<option value="writer" <c:if test="${condition eq 'writer' }">selected</c:if> >작성자</option>
		</select>
		<input type="text" value="${keyword }" name="keyword" placeholder="검색어..." />
		<button type="submit">검색</button>
	</form>
</div>
</body>
</html>