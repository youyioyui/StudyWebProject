<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<th>USERNO</th>
			<td>${userno}</td>
		</tr>
		<tr>
			<th>USERID</th>
			<td>${ud.userId}</td>
		</tr>
		<tr>
			<th>USEROLE</th>
			<td>${ud.userRole}</td>
		</tr>
		<tr>
			<th>DEPT</th>
			<td>${ud.dept}</td>
		</tr>
	</table>

	<div>
	댓글
		<form id="userReplyForm">
			<textarea name="userReplyContent" id="userReplyContent" rows="4" cols="50"></textarea>
			<input type="hidden" class="usernoHidden" name="userno" value="${userno}"> 
			<input type="button" value="Reply" onclick="submitUserReply()">
		</form>
			<%--
		<table border =1>
			<c:forEach var="rl" items="${rfl}">
					<tr>
						<td class="a-c">${rl.order}</td>
						<td class="a-c">${rl.user}</td>
						<td class="a-c">${rl.reMemo}</td>
						<td class="a-c"><fmt:formatDate
							pattern="yyyy년 MM월 dd일 hh시 mm분" value="${rl.regdate}" /></td>
						<td>
							<form id="replymodify">
								<input type="hidden" class="renohidden" name="renoh" value="${rl.reNo}"> 
								<input type="hidden" class="usernohidden" name="usernoh" value="${rl.userNo}"> 
								<c:if test="${sessionNO eq rl.userNo || sessionNo eq 1 }">
									<input type="button" value="Reply" onclick="deleteReply(${rl.reNo},${rl.userNo} )">
								</c:if>							
							</form>
						</td>
					</tr>
			</c:forEach>
		</table>
	</div>
	 --%>
</body>
</html>