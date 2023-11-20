package com.user.controller;

import java.io.IOException;
import java.net.http.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.dao.UserDAO;
import com.user.vo.LoginVO;
import com.user.vo.UserListVO;

@WebServlet("/user/login")
public class LoginController extends HttpServlet{
	private static final int cookieExp = 60*60*24*7;  //60초x60분x24시간x7일 쿠키 유통기한은 7일
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aid = getCookie("sid", req);	//쿠키가 있다면, 쿠키를 부셔서 id 값을 불러온다.
		req.setAttribute("id", aid);	// 쿠키 내 id 값을 가져와 login.jsp 로 보낸다.

		req.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(req, resp);
		
	}
	
	private String getCookie(String cid, HttpServletRequest req){
		
		String rid ="";
		
		if(req == null) {  //1. 사용자 요청 없이 그냥 로그인 화면만 띄울 경우
			return rid;
		}
		
		Cookie[] cookies = req.getCookies(); // 사용자 쿠키 소유 여부
		
		if(cookies ==null){  // 사용자가 쿠키를 소유하지 않았다면
			return rid;
		}
		
		for(Cookie ck : cookies) {
			if(ck.getName().equals(cid)) {	//쿠키 값 중에 cid 값과 동일한 것이 있다면
				rid = ck.getValue();	//쿠키 값 불러오기
				
				System.out.println("ck.getname : "  + ck.getName());
				System.out.println("ck.getValue : "  + ck.getValue());
				System.out.println("rid : "  + rid);
				
				break;
			}
		}
		return rid;
	}
	
	private void makeCookie(String cid, String pid, HttpServletResponse resp) {
		Cookie ck = new Cookie(cid, pid);
		ck.setPath("/");
		ck.setMaxAge(cookieExp);
		resp.addCookie(ck);
	}
	
	protected void loginFail(HttpServletRequest req, HttpServletResponse resp, String ment) throws ServletException, IOException {
		
		req.setAttribute("ment", ment);
		req.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		String remember = req.getParameter("remember");
		System.out.println("remember : " + remember);
		String pid = req.getParameter("id");
		String ppass = req.getParameter("pass");

		LoginVO lv = new LoginVO(pid, ppass);
		UserDAO ud = new UserDAO();
		
		int result = ud.loginCheck(lv);
		String ment = "";
		
		if(result==1) {
			System.out.println("로그인 성공하였습니다.");

			UserListVO uv = ud.getUserInfo(lv);
			
			if(remember!=null) {	// remember id 체크 시
				makeCookie("sid", pid, resp);	//쿠키에 id 각인 서비스
			}else{
				makeCookie("sid", "", resp);	//쿠키만 그냥 줌
			}
			
			HttpSession session = req.getSession();
			
			session.setAttribute("userID", uv.getUserId());
			session.setAttribute("userNm", uv.getUserNm());
			session.setAttribute("role", uv.getUserRole());
			session.setAttribute("userNO", uv.getUserNo());
			session.setAttribute("dept", uv.getDept());
			
					
			resp.sendRedirect("/board/list");
		}else if(result==0) {
			ment = "ID 와 PASSWORD 확인해주세요.";
			loginFail(req,resp,ment);
		}else {
			ment = "로그인에 실패하였습니다.";
			loginFail(req,resp,ment);
		}	
	}
}
