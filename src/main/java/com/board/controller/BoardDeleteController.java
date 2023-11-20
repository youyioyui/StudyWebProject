package com.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;

@WebServlet("/board/delete")
public class BoardDeleteController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String brdno = req.getParameter("brdno");
		
		System.out.println("delete brdno : " + brdno);
		
		int result = BoardDAO.getInstance().deleteBoard(brdno);
		resp.sendRedirect("/board/list");
	}
}
