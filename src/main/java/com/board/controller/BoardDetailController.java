package com.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.board.dao.BoardDAO;
import com.board.vo.BoardListVO;
import com.board.vo.BoardReplyVO;

@WebServlet("/board/detail")
public class BoardDetailController extends HttpServlet {
	private static final int cookieExp = 60 * 60 * 24 * 7; // 60초x60분x24시간x7일 쿠키 유통기한은 7일

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//세션 체크
		if (req.getSession().getAttribute("userID")==null) { //로그인 안 하면 로그인 창으로 보냄
			resp.sendRedirect("/user/login");
		}else { // 로그인 상태에서는 프로세스 진행
	
			/*필요 변수 선언 */
			// brdno 파라미터 값
			String param = req.getParameter("brdno"); //url 뒤에 붙은 글번호 파라미터
			// 쿠키의 brdno 값
			String cbrdno = getCookie("brdno", req); //쿠키에 각인된 글번호 
			
			// 세션 값 호출
			String sid = req.getSession().getAttribute("userID").toString();
			String sno = req.getSession().getAttribute("userNO").toString();
			
			System.out.println("USERNO Con: " + sno);
			
			//검색조건 파라미터 추가
			String f = req.getParameter("f"); //title, name
			String q = req.getParameter("q"); //검색어
			String p = req.getParameter("p"); //글목록 페이지 번호
			String rp_ = req.getParameter("rp"); // 댓글목록 페이지번호
			
			int rp = 1;
			
			if(rp_!=null && rp_!="") {
				rp = Integer.parseInt(rp_);
			}
			
			//검색조건 PagingVO 객체에 담음
			PagingVO pv = new PagingVO(f, q, p, rp);

			// 형변환을 위한 변수선언
			int brdno = 0;
			
			// 글쓴이
			String wid = "";
			//파라미터(brdno) 가 null 이 아니고, 빈값이 아니라면
			if (param != null && param != "") {
				brdno = Integer.parseInt(param);   //파라미터를 글자에서 숫자로 형변환
				wid = BoardDAO.getInstance().getWriteUser(param); //wid 에 글쓴이 id 입력
			}
			// 글을 한번도 읽은 적이 없고, 작성자와 읽은이가 동일하지 않을 경우
			if (!cbrdno.equals(param) && !sid.equals(wid)) { //쿠키에 각인된 brdno 와 파라미터의(brdno) 가 동일하지 않고, session id 와 글쓴이id 가 동일하지 않을때
				BoardDAO.getInstance().plusHit(brdno); // 조회수 1 증가
			}
			
			/* 조회 부분 */
			// 디테일 데이터 조회
			BoardListVO bv = BoardDAO.getInstance().getBoardDetail(brdno); 
			// 댓글 카운트
			int rpCnt = BoardDAO.getInstance().getReplyCount(brdno);
			
			// 댓글 조회 (매개변수로 글번호, 댓글 페이지 번호 보냄)
			List<BoardReplyVO>rflist = BoardDAO.getInstance().getReplyList(brdno, rp);
			
			/* Jsp set Attribute */
			//brdno 를 detail.jsp 에 전달
			req.setAttribute("brdno", param);
			
			//접속한 사용자 USERNO 
			req.setAttribute("sessionNO", sno);
			//댓글 갯수 count
			req.setAttribute("rpCnt", rpCnt);
			
			//파라미터 목록 (f : title, name , q : 검색어, p : 글 목록 페이지)
			req.setAttribute("pv", pv);
			
			//detail.jsp 에 attribute 댓글 목록을 전달  
			req.setAttribute("rfl", rflist);
			
			//detail.jsp 에 attribute 글상세 전달(조회한 데이터)
			req.setAttribute("bd", bv);
			
			// 글을 조회 후 한번 읽어옃본 사람도, 읽지 않은 사람도 모두 쿠키 증정
			makeCookie("brdno", param, resp); 
			
			req.getRequestDispatcher("/WEB-INF/jsp/board/detail.jsp").forward(req, resp);
		}
		
	}

	private String getCookie(String name, HttpServletRequest req) {

		String brdno = "";
		Cookie[] cookies = req.getCookies(); // 사용자 쿠키 소유 여부
		if (cookies == null) { // 사용자가 쿠키를 소유하지 않았다면
			return brdno;
		}

		for (Cookie ck : cookies) {
			if (ck.getName().equals(name)) { // 쿠키 값 중에 cid 값과 동일한 것이 있다면
				brdno = ck.getValue(); // 쿠키 값 불러오기
				System.out.println("ck.getname : " + ck.getName());
				System.out.println("ck.getValue : " + ck.getValue());
				System.out.println("brdno : " + brdno);
				break;
			}
		}
		return brdno;
	}

	private void makeCookie(String name, String brdno, HttpServletResponse resp) {
		Cookie ck = new Cookie(name, brdno);
		ck.setPath("/");
		ck.setMaxAge(cookieExp);
		resp.addCookie(ck);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		//세션에서 userid 받아오기
		String userID = req.getSession().getAttribute("userID").toString();
		String userNo = req.getSession().getAttribute("userNo").toString();
		
		//fetch 요청 건 처리
		StringBuilder buffer = new StringBuilder(); // String builder 객체 생성
	
		BufferedReader reader = req.getReader();  //1. javascript 으로 부터 온 요청(json)을 읽어 reader에 저장
		String line="";
		while ((line = reader.readLine()) != null) { //2.  String line 에 reader 안 데이터를 한줄 씩 읽어 입력한다.
			buffer.append(line); //3. buffer 에 reader 데이터를 추가한다.
		}
		String payload = buffer.toString();	//4. StringBuilder 값을 하나의 String 으로 변환하여 payload 에 입력한다.
		JSONObject json = new JSONObject(payload);	//5. payload 의 문자열을 json 객체로 변환한다.
		int brdno = json.getInt("brdno");
		String reMemo = json.getString("replyContent");	
		
	//댓글 내용 확인
	 System.out.println("brd : " + brdno);
	 System.out.println("reply : " + reMemo);
	 
	 BoardReplyVO rv = new BoardReplyVO(userID, brdno, reMemo);
	 BoardDAO.getInstance().insertReply(rv); //insert 댓글
	 
	 JSONObject responseJson = new JSONObject();
	 responseJson.put("status", "Success");
	 responseJson.put("message", "Reply submitted successfully.");
	 
	 resp.setContentType("application/json");
	 resp.setCharacterEncoding("UTF-8");
	 
	 resp.getWriter().write(responseJson.toString());
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		//세션에서 userid 받아오기
		String userID = req.getSession().getAttribute("userID").toString();
		
		//fetch 요청 건 처리
		StringBuilder buffer = new StringBuilder(); // String builder 객체 생성
			
		BufferedReader reader = req.getReader();  //1. javascript 으로 부터 온 요청(json)을 읽어 reader에 저장
		String line="";
		while ((line = reader.readLine()) != null) { //2.  String line 에 reader 안 데이터를 한줄 씩 읽어 입력한다.
			buffer.append(line); //3. buffer 에 reader 데이터를 추가한다.
		}
		String payload = buffer.toString();	//4. StringBuilder 값을 하나의 String 으로 변환하여 payload 에 입력한다.
		JSONObject json = new JSONObject(payload);	//5. payload 의 문자열을 json 객체로 변환한다.
		
		int userNo = json.getInt("userno");
		int reNo = json.getInt("reno");	
		
		System.out.println("userno : " + userNo);
		System.out.println("reNo : " + reNo);
		
		BoardDAO.getInstance().deleteReply(userNo, reNo);
		
		JSONObject responseJson = new JSONObject();
		responseJson.put("status", "Success");
		responseJson.put("message", "Reply delete successfully.");
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(responseJson.toString());
	}
	
}
