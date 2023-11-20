<%@page import="com.board.vo.BoardListVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src=/resources/js/test2.js></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>ID</td>
			<td>이름</td>
			<td>직무</td>
			<td>부서</td>
			<td>등록일</td>
		</tr>
		<!-- 리스트 시작 -->

		<c:forEach var="list" items="${ul}">
			<tr>
				<td>${list.userId}</td>
				<td><a href="/user/detail?userno=${list.userNo}">${list.userNm} </a>
				</td>
				<td>${list.userRole}</td>
				<td>${list.dept}</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<form id="userReplyForm">
			<textarea name="userReplyContent" id="userReplyContent" rows="4" cols="50"></textarea>
			<input type="hidden" class="usernoHidden" name="userno" value="${userno}"> 
			<input type="button" value="Reply" onclick="submitUserReply()">
		</form>
	
	</div>
	
	<!-- 검색 시작 -->
	<form action="" method="get">
		<div>
			<select name="f">
				<option ${(param.f=="usernm")?"selected":""} value="usernm">이름</option>
				<option ${(param.f=="userrole")?"selected":""} value="userrole">직무</option>
				<option ${(param.f=="dept")?"selected":""} value="dept">부서</option>
			</select> <input type="text" name="q"> 
			<span><input type="submit"></span>
		</div>
	</form>
</body>
</html>