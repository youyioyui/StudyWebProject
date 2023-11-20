package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dao.BoardDAO;
import com.board.vo.BoardRegeditVO;

@WebServlet("/board/regedit")
public class BoardRegeditController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//세션체크
		if (req.getSession().getAttribute("userID")==null) {
			System.out.println("222222222222");
			resp.sendRedirect("/user/login");
		}else {
			System.out.println("111111111111");
			req.getRequestDispatcher("/WEB-INF/jsp/board/regedit.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		//세션 값
		String userId = req.getSession().getAttribute("userID").toString();
		String userNm = req.getSession().getAttribute("userNm").toString();
		String dept = req.getSession().getAttribute("dept").toString();
		//파라미터 값
		String brdTitle = req.getParameter("brdtitle");
		String brdMemo = req.getParameter("brdmemo");
		String ment = "";
		
		if(brdTitle==null || brdTitle =="") {
			ment = "제목을 입력해주세요.";
			ment(req,resp,ment);
		}else if(brdTitle.length()>=255) {
			ment = "제목이 너무 깁니다 255자 까지 입력가능합니다.";
			ment(req,resp,ment);
		}else if(brdMemo== null || brdMemo =="") {
			ment = "본문을 입력해주세요.";
			ment(req,resp,ment);
		}else if(brdMemo.length()>=1000) {
			ment = "내용이 너무 깁니다. 적당히 하세요.";
			ment(req,resp,ment);
		}
		
		System.out.println("val : " + userId+ "," + userNm + "," + dept + ", " +brdTitle);
		BoardRegeditVO bv = new BoardRegeditVO(brdTitle, userNm, brdMemo, dept, userId);
//		bv.setUser(userId);
//		bv.setUserNm(userNm);
//		bv.setDept(dept);
//		bv.setTitle(brdTitle);
//		bv.setMemo(brdMemo);
		int result = BoardDAO.getInstance().regeditBoard(bv);
		
		if(result>=1) {
			resp.sendRedirect("/board/list");
		}else {
			doGet(req, resp);
		}		
	}
	
	public void ment(HttpServletRequest req, HttpServletResponse resp, String ment) throws ServletException, IOException {
		req.setAttribute("ment", ment);
		doGet(req, resp);
	}
}
