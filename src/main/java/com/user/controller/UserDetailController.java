package com.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.board.dao.BoardDAO;
import com.board.vo.BoardListVO;
import com.board.vo.BoardReplyVO;
import com.user.dao.UserDAO;
import com.user.vo.UserListVO;
import com.user.vo.UserReplyVO;

@WebServlet("/user/detail")
public class UserDetailController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 세션 체크
		if (req.getSession().getAttribute("userID") == null) { // 로그인 안 하면 로그인 창으로 보냄
			resp.sendRedirect("/user/login");
		} else { // 로그인 상태에서는 프로세스 진행

			/* 필요 변수 선언 */
			// brdno 파라미터 값
			String userno = req.getParameter("userno"); // url 뒤에 붙은 글번호 파라미터

			// 세션 값 호출
			String sid = req.getSession().getAttribute("userID").toString();
			String sno = req.getSession().getAttribute("userNO").toString();

			/* 조회 부분 */
			// 디테일 데이터 조회
			UserListVO uv = UserDAO.getInstance().getUserDetail(userno);

			// 댓글 조회 (매개변수로 글번호, 댓글 페이지 번호 보냄)
			//List<BoardReplyVO> rflist = BoardDAO.getInstance().getReplyList(brdno, rp);

			/* Jsp set Attribute */
			// brdno 를 detail.jsp 에 전달
			req.setAttribute("userno", userno);

			// 접속한 사용자 USERNO 
			req.setAttribute("sessionNO", sno);

			// detail.jsp 에 attribute 댓글 목록을 전달
		//	req.setAttribute("rfl", rflist);

			// detail.jsp 에 attribute 글상세 전달(조회한 데이터)
			req.setAttribute("ud", uv);

			req.getRequestDispatcher("/WEB-INF/jsp/user/detail.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//인코딩
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		// 세션 값 호출
		String sid = req.getSession().getAttribute("userID").toString();
		String sessionUserNo = req.getSession().getAttribute("userNO").toString();
		
		//fetch 요청 건 처리
		StringBuilder buffer = new StringBuilder(); // String builder 객체 생성
	
		BufferedReader reader = req.getReader();  //1. javascript 으로 부터 온 요청(json)을 읽어 reader에 저장
		String line="";
		while ((line = reader.readLine()) != null) { //2.  String line 에 reader 안 데이터를 한줄 씩 읽어 입력한다.
			buffer.append(line); //3. buffer 에 reader 데이터를 추가한다.
		}
		String payload = buffer.toString();	//4. StringBuilder 값을 하나의 String 으로 변환하여 payload 에 입력한다.
		JSONObject json = new JSONObject(payload);	//5. payload 의 문자열을 json 객체로 변환한다.
		//json 값 가져오기
		int targetUserno = json.getInt("userno");
		String rememo  = json.getString("userReplyContent");	
		
		System.out.println("con-targetUserno : " + targetUserno );
		System.out.println("con-rememo : " + rememo );
		
		UserReplyVO uv = new UserReplyVO(sessionUserNo, targetUserno , rememo);
		
		UserDAO.getInstance().userReplyInsert(uv);
	
		 JSONObject responseJson = new JSONObject();
		 responseJson.put("status", "Success");
		 responseJson.put("message", "Reply submitted successfully.");
		 
		 resp.setContentType("application/json");
		 resp.setCharacterEncoding("UTF-8");
		 
		 resp.getWriter().write(responseJson.toString());
	
	}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
}
