<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>${ment}</p>
	<form action="" method="post">
	<table border="1">
		<tr>
			<th>제목</th>
			<td><input type="text" name="brdtitle"></td>
		</tr>
		<tr>
			<th>글내용</th>
			<td><textarea name="brdmemo"></textarea>
			</td>
		</tr>
	</table>
	<input type="submit" value="저장"/>
	</form>
</body>
</html>