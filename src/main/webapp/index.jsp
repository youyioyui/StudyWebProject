<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:choose>
		<c:when test="${sessionScope.userID eq null}">
			<jsp:forward page="/user/login"></jsp:forward>
		</c:when>
		<c:otherwise>
			<jsp:forward page="/board/list"></jsp:forward>
		</c:otherwise>
	</c:choose>
