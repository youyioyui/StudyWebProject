package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.board.dao.BoardDAO;
import com.board.vo.BoardListVO;
import com.user.vo.LoginVO;
import com.user.vo.SignUpVO;
import com.user.vo.UserListVO;
import com.user.vo.UserReplyVO;
import com.web.connection.ConnectionProvider;
import com.web.connection.JdbcUtil;

public class UserDAO {
	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

//	public int loginCheck(LoginVO lv) {
//		
//		String url = "jdbc:mysql://localhost:3306/study?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//		String id = "root";
//		String pass = "1234";
//		String driver = "com.mysql.cj.jdbc.Driver";
//
//		//PreparedStatement psmt = null;
//		Statement st = null;
//		ResultSet rs = null;
//		
//		String pid = lv.getPid();
//		String ppass = lv.getPpass();
//		
//		System.out.println("pid : " + pid);
//		System.out.println("ppass : " + ppass);  //controller 에서 던진 값이 정상적인지
//		
//		int result =0;
//		
//		String sql = "select count(*) as cnt"
//				+ "  from com_user"
//				+ " where userid = '"+ pid +"' "
//				+ "   and userpw = sha2('"+ ppass +"', 256);";
//
//		try {
//			Class.forName(driver);
//			Connection con = DriverManager.getConnection(url, id, pass);
//			st = con.createStatement();
//			rs = st.executeQuery(sql);
//			System.out.println(sql);  //sql 정상유무
//			
//			if(rs.next()) {
//				result = rs.getInt("cnt");
//				System.out.println("result : " + result);  //결과값 확인
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	public int loginCheck(LoginVO lv) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String pid = lv.getPid();
		String ppass = lv.getPpass();
		
		System.out.println("pid : " + pid);
		System.out.println("ppass : " + ppass);  //controller 에서 던진 값이 정상적인지
		
		int result =0;
		
		String sql = "select count(*) as cnt"
				+ "  from com_user"
				+ " where userid = ? "
				+ "   and userpw = sha2( ?, 256);";

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			psmt.setString(2, ppass);
			rs = psmt.executeQuery();
			System.out.println(sql);  //sql 정상유무
			
			if(rs.next()) {
				result = rs.getInt("cnt");
				System.out.println("result : " + result);  //결과값 확인
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<UserListVO> getUserList(String sql_, String q) {
		Connection con = null;
		PreparedStatement psmt = null;
		// Statement st = null;
		ResultSet rs = null;

		String sql = "select cu.USERNO as userno,	"
				+ "	   cu.USERID as userid, "
				+ "	   cu.USERNM as usernm, "
				+ "	   ce.CODENM as userrole, "
				+ "	   cd.DEPTNM as dept "
				+ "  from com_user cu, "
				+ "  	   com_dept cd, "
				+ "  	   com_code ce "
				+ " where cu.DEPTNO = cd.DEPTNO "
				+ "   and cu.USERROLE = ce.CODECD ";
		
		sql = sql + sql_;
		
		List<UserListVO>list = new ArrayList<UserListVO>();
		
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "%" + q + "%");
			rs = psmt.executeQuery();
			System.out.println(sql);  //sql 정상유무
			
			while(rs.next()) {
				int userno = rs.getInt("userno");
				String userid = rs.getString("userid");
				String usernm = rs.getString("usernm");
				String userrole = rs.getString("userrole");
				String dept = rs.getString("dept");
				
				UserListVO uv = new UserListVO(userno, userid, usernm, userrole, dept);
				
				list.add(uv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public UserListVO getUserInfo(LoginVO lv) {
		Connection con = null;
		PreparedStatement psmt = null;
		// Statement st = null;
		ResultSet rs = null;
		
		String sql = "select cu.USERNO as userno, "
				+ "			 cu.USERID as userid, "
				+ "			 cu.USERNM as usernm, "
				+ "			 cu.DEPTNO as dept, "
				+ "			 cu.USERROLE as userrole "
				+ "		from com_user cu "
				+ "	   where cu.USERID = ? ";
				
		UserListVO uv = null;
		
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, lv.getPid());
			rs = psmt.executeQuery();
			System.out.println(sql);  //sql 정상유무
			
			 if (rs.next()) {
				int userno = rs.getInt("userno");
				String userid = rs.getString("userid");
				String usernm = rs.getString("usernm");
				String userrole = rs.getString("userrole");
				String dept = rs.getString("dept");
				
				uv = new UserListVO(userno, userid, usernm, userrole, dept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uv;
	}

	public int getIdCheck(String param) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String pid = param;
		
		int result =0;
		
		String sql = "select count(*) as cnt"
				+ "  from com_user"
				+ " where userid = ? ";

		try {
			con = ConnectionProvider.getConnection();

			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			rs = psmt.executeQuery();
			System.out.println(sql);  //sql 정상유무
			
			if(rs.next()) {
				result = rs.getInt("cnt");
				System.out.println("result : " + result);  //결과값 확인
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void SignupUser(SignUpVO sv) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
	
		String sql = " insert into com_user(USERID , USERNM, USERPW, USERROLE, DEPTNO) "
				   + " values(?, ?, sha2(?, 256), 'U', 1)";

		String pid = sv.getUserId();
		String ppass = sv.getUserPw();
		String pusernm = sv.getUserNm();
		
		try {
			con = ConnectionProvider.getConnection();
			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pid);
			psmt.setString(2, pusernm);
			psmt.setString(3, ppass);
			psmt.executeUpdate();
			
			System.out.println(sql);  //sql 정상유무
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserListVO getUserDetail(String userno) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = "select userid, "
				+ "	   usernm, "
				+ "	   (select cd.deptnm "
				+ "	      from com_dept cd "
				+ "	     where cd.DEPTNO = cu.DEPTNO) as dept, "
				+ "	   (select cc.CODENM "
				+ "	      from com_code cc "
				+ "	     where cc.CODECD = cu.USERROLE) as userrole "
				+ "  from com_user cu "
				+ " where cu.userno = ? ";

		UserListVO uv = null;
		int cuserno = Integer.parseInt(userno);
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, cuserno);
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			if (rs.next()) {
				String userID = rs.getString("userid");
				String usernm = rs.getString("usernm");
				String dept = rs.getString("dept");
				String userrole = rs.getString("userrole");

				uv = new UserListVO(userID, usernm, dept, userrole);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return uv;
	}

	public void userReplyInsert(UserReplyVO uv) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "insert into com_userreply "
				+ " (USERNO, BACKMEMO, REPARENT, REDEPTH, REORDER, REDATE, REDELETEFLAG)"
				+ "	   SELECT"
				+ "	   ? , "
				+ "	   ? , "
				+ "	   ? , "
				+ "	   1 , "
				+ "	    IFNULL((select MAX(REORDER) "
				+ "              from com_userreply "
				+ "             where USERNO = ?), 1) + 1 as REORDER , "
				+ "	   	now() as REDATE, "
				+ "    'N' as REDELETEFLAG ) ";
		
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			int sessionUserNo = uv.getUserNo(); // BRDTITLE
			String backmemo = uv.getBackmemo();
			int targetUserNo = uv.getUserno(); // USERNO, LASTUSERNO

			psmt.setInt(1, sessionUserNo);
			psmt.setString(2, backmemo);
			psmt.setInt(3, targetUserNo);
			psmt.setInt(4, targetUserNo);

			psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);
	}
}
