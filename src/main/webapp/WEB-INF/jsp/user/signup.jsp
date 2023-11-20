<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
<h1>회원가입</h1>
</div>
${ment}
	<form action="" method="post">
		<table border=1>
			<tr>
				<td><label>ID</label>
				<td><input placeholder="ID" type="text" id="id" name="id" ></td>
			</tr>
			<tr>
				<td><label>PASSWORD</label>
				<td><input placeholder="PASSWORD" type="password" id="pass" name="pass"></td>
			</tr>
			<tr>
				<td><label>PASSWORD CHECK</label>
				<td><input placeholder="동일한 PASSWORD 를 입력하세요" type="password" id="pass" name="passchk"></td>
			</tr>
			<tr>
				<td><label>NAME</label>
				<td><input placeholder="NAME" type="text" id="name" name="name"></td>
			</tr>
			<tr>
				<td><label>EMAIL</label>
				<td><input placeholder="AAA@AAAA.COM" type="email" id="email" name="email"></td>
			</tr>
		</table>
		<input type="submit" value="회원가입">
		<input type="button" value="로그인" onClick="location.href='/user/login'">
	</form>
</body>
</html>