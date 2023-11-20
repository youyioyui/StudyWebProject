<%@page import="com.board.vo.BoardListVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/global.css" />
<script type="text/javascript" src=/resources/js/test1.js></script>

<%-- 
	<%
	//	List<BoardListVO>list = (List<BoardListVO>)request.getAttribute("bl");
	%>
 --%>
</head>
<body>
	<div class="wrap">
		<h3 class="a-c">
			<strong class="color-crim">${dept} 부서의 ${userNm} </strong>님 반갑습니다.
		</h3>
		<div>
		<input type="button" value="User List" onclick="location.href='/user/list'" class="btn-logout" />
		</div>
		<div>
		<input type="button" value="Log Out" onclick="location.href='/user/logout'" class="btn-logout" />
		</div>
		<table>
			<tr>
				<td>ID</td>
				<td>제목</td>
				<td>내용</td>
				<td>글쓴이</td>
				<td>등록일</td>
				<td>조회수</td>
			</tr>
			<!-- 리스트 시작 -->

			<c:forEach var="list" items="${bl}">
				<tr>
					<td class="a-c">${list.brdno}</td>
					<td class="td-tit"><a href="/board/detail?brdno=${list.brdno}&p=${param.p}&q=${param.q}&f=${param.f}">${list.title} </a>[${list.replyCount}]</td>
					<td class="a-c">${list.memo}</td>
					<td class="a-c">${list.user}</td>
					<td class="a-c"><fmt:formatDate
							pattern="yyyy년 MM월 dd일 hh시 mm분" value="${list.regdate}" /></td>
					<td class="a-c">${list.hit}</td>
				</tr>
			</c:forEach>
			<%-- 
		<% //for(BoardListVO bl : list){ 
		%>
		
			<tr>
			<td><%= //bl.getBrdno()%></td>
			<td><%= //bl.getTitle()%></td>
			<td><%= //bl.getUser()%></td>
			<td><%= //bl.getMemo()%></td>
			<td><%= //bl.getRegdate()%></td>
			<td></td>
		</tr>
		
		<%
		//}
		%>
		--%>

		</table>
		</div>

		<%-- 현재 페이지에 대한 변수 선언 --%>
		<c:set var="page" value="${empty param.p?1:param.p}"></c:set>
		<c:set var="startNum" value="${page-(page-1)%5}"></c:set>
		<c:set var="lastNum"
			value="${fn:substringBefore(Math.ceil(cnt/10),'.')}"></c:set>

		<%-- 전체 건수 --%>
		<div class="paging">
			<p>
				총 <strong class="color-dbl"> ${cnt}</strong> 건이 조회 되었습니다.
		</div>
		<%-- 현재 페이지 표시 --%>
		<h2>${page} / ${lastNum} pages</h2>

		<%-- 화살표를 이용한 페이지 이동 이전 --%>
		<div style="display: flex; align-items: center">
			<c:if test="${startNum >1 }">
				<a href="?p=${startNum-1}&f=${param.f}&q=${param.q}">prev</a>
			</c:if>
			<c:if test="${startNum <=1 }">
				<a href="javascript:;" onclick="alert('첫 페이지입니다.')">prev</a>
			</c:if>


			<%-- 숫자 페이지 이동 --%>
			<ul style="display: flex; padding-right: 40px;">
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${param.p==(startNum+i)}">
						<c:set var="style" value="font-weight:bold; color:red;" />
					</c:if>
					<c:if test="${param.p!=(startNum+i)}">
						<c:set var="style" value="" />
					</c:if>
					<c:if test="${(startNum+i)<=lastNum }">
						<li style="margin-right: 10px; list-style: none;"><a
							style="${style} "
							href="?p=${startNum+i}&f=${param.f}&q=${param.q}">${startNum+i}</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>

			<%-- 화살표를 이용한 페이지 이동 다음 --%>
			<c:if test="${startNum+5 <= lastNum}">
				<a href="?p=${startNum+5}&f=${param.f}&q=${param.q}">next</a>
			</c:if>

			<c:if test="${startNum+5 > lastNum}">
				<a href="javascript:;" onclick="alert('마지막 페이지입니다.')">next</a>
			</c:if>
		</div>

	<div>
		<input type="text" id="test1" class="test" />
		<input type="text" id="test2" class="testi" />
		<input type="button" onclick="myevent()" value="선택">
	</div>

	<%-- 글쓰기 --%>
	<div class="ma-t-1 a-r">
		<input type="button" onclick="location.href='/board/regedit'" value="글쓰기"" >
	</div>

	<%-- 검색 --%>
	<form action="" method="get">
		<div>
			<select name="f">
				<option ${(param.f=="title")?"selected":""} value="title">제목</option>
				<option ${(param.f=="user")?"selected":""} value="user">글쓴이</option>
			</select> <input type="text" name="q"> <span><input
				type="submit"></span>
		</div>
	</form>
</body>
</html>