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
			<th>글쓴이</th>
			<td>${bd.user}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${bd.title}</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>${bd.regdate}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${bd.hit}</td>
		</tr>
		<tr>
			<th colspan="2">내용</th>
		</tr>
		<tr>
			<td colspan="2">${bd.memo}</td>
		</tr>
	</table>
	<div>
		<input type="button" onclick="location.href='update?brdno=${brdno}'"
			value="글수정">
	</div>
	<div>
		<input type="button" onclick="location.href='delete?brdno=${brdno}'"
			value="글삭제">
	</div>
	<div>
	<div>
	댓글
	</div>
		<form id="replyForm">
			<textarea name="replyContent" id="replyContent" rows="4" cols="50"></textarea>
			<input type="hidden" class="brdnohidden" name="brdno" value="${brdno}"> 
			<input type="button" value="Reply" onclick="submitReply()">
		</form>
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
	
	<%-- 현재 페이지에 대한 변수 선언 --%>
		<c:set var="rfPage" value="${empty param.rp?1:param.rp}"></c:set>
		<c:set var="rfStartNum" value="${rfPage-(rfPage-1)%5}"></c:set>
		<c:set var="rfLastNum" value="${fn:substringBefore(Math.ceil(rpCnt/10),'.')}"></c:set>
	<div style="display: flex; align-items: center">
	
	<%-- 숫자 페이지 이동 --%>
			<ul style="display: flex; padding-right: 40px;">
				<c:forEach var="i" begin="0" end="4">
					<c:if test="${param.rf==(rfStartNum+i)}">
						<c:set var="style" value="font-weight:bold; color:red;" />
					</c:if>
					<c:if test="${param.rf!=(rfStartNum+i)}">
						<c:set var="style" value="" />
					</c:if>
					<c:if test="${(rfStartNum+i)<=rfLastNum }">
						<li style="margin-right: 10px; list-style: none;"><a
							style="${style} "
							href="?brdno=${brdno}&p=${pv.p}&f=${pv.f}&q=${pv.q}&rp=${rfStartNum+i}">${rfStartNum+i}</a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
	</div>
</body>
</html>