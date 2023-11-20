<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
${ment}
</div>
	<form action="" method="post">
		<table border=1>
			<tr>
				<td><label>ID</label>
				<td><input placeholder="ID" type="text" id="id" name="id" 
				value="<c:out value='${id}'/>"> </td>
			</tr>
			<tr>
				<td><label>PASSWORD</label>
				<td><input placeholder="PASSWORD" type="password" id="pass" name="pass"></td>
			</tr>
		</table>
		<input type="submit" value="로그인">
		<input type="button" value="회원가입" onClick="location.href='/user/signup'">
		<input type="checkbox" name="remember" value="Y" <c:if test='${id !=null && id!=""}'>checked</c:if>>Remember ID
		
		
		
		
		
		
</form>
</body>
</html>