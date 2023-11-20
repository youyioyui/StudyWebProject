package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.vo.BoardListVO;
import com.board.vo.BoardRegeditVO;

@WebServlet("/board/update")
public class BoardUpdateController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("brdno");	
		int brdno = 0;
		
		if(param!=null && param !="") {
			brdno = Integer.parseInt(param);
		}
		
		BoardDAO bd = new BoardDAO();
		
		BoardListVO bv = bd.getBoardDetail(brdno);
		
		req.setAttribute("brdno", param);
		req.setAttribute("bd", bv);
		
		req.getRequestDispatcher("/WEB-INF/jsp/board/update.jsp").forward(req, resp);
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
		int brdno = Integer.parseInt(req.getParameter("brdno"));
		
		System.out.println("val : " + userId+ "," + userNm + "," + dept + ", " +brdTitle);
		
		BoardRegeditVO bv = new BoardRegeditVO(brdno, brdTitle, userNm, brdMemo, dept, userId);
		
		int result = BoardDAO.getInstance().updateBoard(bv);
		
		if(result>=1) {
			resp.sendRedirect("/board/list");
		}else {
			doGet(req, resp);
		}		
	}
}
