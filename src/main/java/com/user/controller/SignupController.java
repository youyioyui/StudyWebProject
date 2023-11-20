package com.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.dao.UserDAO;
import com.user.vo.SignUpVO;

@WebServlet("/user/signup")
public class SignupController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/user/signup.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		String pid = req.getParameter("id");
		String ppass = req.getParameter("pass");
		String ppasschk = req.getParameter("passchk");
		String pname = req.getParameter("name");
		String pemail = req.getParameter("email");
		
		String ment = "";
		
		System.out.println("param id : " + pid);
		System.out.println("param pass : " + ppass);
		System.out.println("param passchk : " + ppasschk);
		System.out.println("param name : " + pname);
		System.out.println("param email : " + pemail);
		
		UserDAO ud = new UserDAO();
		int idchk = ud.getIdCheck(pid);
		
		if(pid.length()>=20) {
			ment = "ID 가 20자 보다 깁니다.";
			ment(req,resp, ment);
		}else if(idchk !=0) {
			ment = "동일한 ID 가 존재합니다.";
			ment(req,resp, ment);
		}else if(pid==null || pid ==""){
			ment = "ID 를 입력해주세요";
			ment(req,resp, ment);
		}else if(ppass==null || ppass ==""){
			ment = "패스워드를 입력해주세요";
			ment(req,resp, ment);
		}else if(ppass.length()<8) {
			ment = "패스워드의 길이는 8자 이상입니다.";
			ment(req,resp, ment);
		}else if(!ppasschk.equals(ppass)) {
			ment ="동일한 패스워드를 입력해주세요.";
			ment(req,resp, ment);
		}else if(pname==null || pname ==""){
			ment = "이름을 입력해주세요";
			ment(req,resp, ment);
		}else if(pname.length()>=20) {
			ment ="넌 오스트리아 귀족이 아니야 짧게 써";
			ment(req,resp, ment);
		}else {
			SignUpVO sv = new SignUpVO(pid, ppass, pname);
			ud.SignupUser(sv);
			resp.sendRedirect("/user/login");
		}

	}
	public void ment(HttpServletRequest req, HttpServletResponse resp, String ment) throws ServletException, IOException {
		req.setAttribute("ment", ment);
		doGet(req, resp);
	}
}
