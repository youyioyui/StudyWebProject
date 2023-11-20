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
	<form action="" method="post">
	<table border="1">
		<tr>
			<th>글쓴이</th>
			<td>${bd.user}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>
				<input type="text" name="brdtitle" value="${bd.title}"></td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>${bd.regdate}</td>
		</tr>
		<tr>
			<th colspan="2">내용</th>
		</tr>
		<tr>
			<td colspan="2"><textarea name="brdmemo" >${bd.memo}</textarea></td>
			
		</tr>
	</table>
	<input type="submit" value="저장"/>
	</form>
	<div>
		<input type="button" onclick="location.href='list'" value="목록으로">
	</div>
</body>
</html>