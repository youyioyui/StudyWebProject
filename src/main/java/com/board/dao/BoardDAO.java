package com.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.board.vo.BoardListVO;
import com.board.vo.BoardRegeditVO;
import com.board.vo.BoardReplyVO;
import com.user.vo.UserListVO;
import com.web.connection.ConnectionProvider;
import com.web.connection.JdbcUtil;

public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();

	public static BoardDAO getInstance() {
		return instance;
	}

	public List<BoardListVO> getBoardList(int p, String g, String q) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int start = 1+(p-1)*10;
		int end =p*10;

		String sql = "select * "
					+ "from(select @rownum:=@rownum+1 as num, n.*"
					+ "		  from (select tb.brdno as brdno,  "
					+ "					   tb.brdtitle as title, "
					+ "					   tb.brdmemo as memo,  "
					+ "					   cu.usernm as user, "
					+ "					   tb.brddate as regdate, "
					+ "					   (select count(*) "
					+ "  					  from tbl_boardreply tr "
					+ " 					 where tr.BRDNO = tb.brdno) as rpcnt, "
					+ "					   tb.hit as hit "
					+ "				  from tbl_board tb, "
					+ "					   com_user cu "
					+ "				 where tb.userno = cu.userno  "
					+ "				   and tb.BRDDELETEFLAG = 'N'";
					
					
		String sql1=  "				 order by regdate desc)n"
					+ "		where (@rownum:=0)=0)num	 "
					+ "where num.num between ? and ?";

		if (g != null) {
			sql = sql + g + sql1;
		} else {
			sql = sql + sql1;
		}
		// + " and tb."+f+" like ? ";
		List<BoardListVO> list = new ArrayList<>();
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "%" + q + "%");
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			while (rs.next()) {
				int brdno = rs.getInt("brdno");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String user = rs.getString("user");
				Date regdate = rs.getDate("regdate");
				int replyCount = rs.getInt("rpcnt");
				int hit = rs.getInt("hit");

				BoardListVO bv = new BoardListVO(brdno, title, memo, user, regdate,replyCount, hit);
				list.add(bv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return list;
	}

	public BoardListVO getBoardDetail(int param) {
		Connection con = null;
		PreparedStatement psmt = null;
		// Statement st = null;
		ResultSet rs = null;

		String sql = "select tb.BRDNO as brdno, " 
				+ "			 cu.USERNM as user, "
				+ "			 tb.BRDTITLE as title, " 
				+ "			 tb.BRDDATE as regdate, "
				+ "			 tb.BRDMEMO as memo, "
				+ "			 tb.hit as hit " 
				+ "		from tbl_board tb, " 
				+ "			 com_user cu "
				+ " where tb.BRDNO = ? " + "   and tb.USERNO =  cu.USERNO ";

		BoardListVO bv = null;

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, param);
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			if (rs.next()) {
				int brdno = rs.getInt("brdno");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String user = rs.getString("user");
				Date regdate = rs.getDate("regdate");
				int hit = rs.getInt("hit");

				bv = new BoardListVO(brdno, title, memo, user, regdate, 0, hit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return bv;
	}

	public int regeditBoard(BoardRegeditVO bv) {
		Connection con = null;
		PreparedStatement psmt = null;
		// Statement st = null;
		ResultSet rs = null;
		String sql = "insert into tbl_board "
				+ "(BGNO, BRDTITLE, BRDMEMO, USERNO, BRDDATE, LASTDATE, LASTUSERNO,BRDDELETEFLAG)"
				+ "values(3,  "
				+ "	   ?,"
				+ "	   ? , "
				+ "	   (select USERNO "
				+ "	      from com_user"
				+ "	     where USERID = ?), "
				+ "	   now(), "
				+ "	   now(), "
				+ "	    (select USERNO "
				+ "	      from com_user"
				+ "	     where USERID = ?), 		"
				+ "	   'N')	";
		int result = 0;
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			String title = bv.getTitle(); // BRDTITLE
			String memo = bv.getMemo();
			String userId = bv.getUser(); // USERNO, LASTUSERNO

			psmt.setString(1, title);
			psmt.setString(2, memo);
			psmt.setString(3, userId);
			psmt.setString(4, userId);

			result = psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return result;
	}

	public int deleteBoard(String brdno) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = " update tbl_board "
				+ " 	set BRDDELETEFLAG = 'Y' "
				+ "   where brdno = ? ";
		int result = 0;
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			
			System.out.println("dao brdno : "+brdno);
			
			int no = Integer.parseInt(brdno);
			
			psmt.setInt(1, no);
			result = psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return result;
	}

	public int getBoardListCount(String g, String q) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = "select count(*) as cnt " 
				+ "		from tbl_board tb, " 
				+ "			 com_user cu "
				+ "	   where tb.userno = cu.userno  "
				+ "		 and tb.BRDDELETEFLAG = 'N' ";

		if (g != null) {
			sql = sql + g;
		} else {
			sql = sql;
		}

		int cnt = 0;
		
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "%" + q + "%");
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return cnt;
	}

	public int updateBoard(BoardRegeditVO bv) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "update tbl_board set BRDTITLE = ? , "
				+ "						   BRDMEMO = ?  , LASTUSERNO = (SELECT USERNO "
				+ "														  FROM com_user "
				+ "														 WHERE USERID = ?)  ,"
				+ "						   LASTDATE = NOW() "
				+ "	  where BRDNO = ? ";
		int result = 0;
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			String title = bv.getTitle(); // BRDTITLE
			String memo = bv.getMemo();
			String userId = bv.getUser(); // USERNO, LASTUSERNO
			int brdno = bv.getBrdno();  //brdno

			psmt.setString(1, title);
			psmt.setString(2, memo);
			psmt.setString(3, userId);
			psmt.setInt(4, brdno);

			psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return result;
	}

	public void plusHit(int brdno) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "update tbl_board set hit = hit+1"
					+ " where brdno = ? ";

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, brdno);
			psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);		
	}

	public String getWriteUser(String param) {
		Connection con = null;
		PreparedStatement psmt = null;
		// Statement st = null;
		ResultSet rs = null;
		String sql = "select (select cu.userid "
				+ "		  from com_user cu "
				+ "		 where cu.USERNO = tb.USERNO)as userid "
				+ "  from tbl_board tb "
				+ " where BRDNO = ? ";
		String result = "";
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			int brdno = Integer.parseInt(param);
			psmt.setInt(1, brdno);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("userid");
			}
			 // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return result;
	}

	public void insertReply(BoardReplyVO rv) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql = "INSERT INTO tbl_boardreply (BRDNO, USERNO, REMEMO, REPARENT, REDEPTH, REORDER, REDATE, REDELETEFLAG, LASTUSERNO) "
				+ "	  	select ? as brdno, "
				+ "	 		(select userno as userno "
				+ "			   from com_user cu "
				+ "			  where USERID = ?)as USERNO, "
				+ "			   ? as rememo, "
				+ "			   ? as reparent, "
				+ "			   1 as redepth, "
				+ "			(select MAX(IFNULL(REORDER, 1)) + 1 "
				+ "			   FROM tbl_boardreply "
				+ "			  WHERE brdno = ?) as reorder , "
				+ "			  now() as redate, "
				+ "			  'N' as REDELETEFLAG,"
				+ "			(select userno as lastuserno "
				+ "			   from com_user cu "
				+ "			  where USERID = ?)as lastuserno ";
		
		System.out.println("sql :" +sql);
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			
			int brdno = rv.getBrdNo(); // brdno
			String userID = rv.getUser(); // userno 로 변환해야 함
			String rememo = rv.getReMemo();
			int parent = rv.getBrdNo();

			psmt.setInt(1, brdno);		//brdno
			psmt.setString(2, userID);	//userno 변환용
			psmt.setString(3, rememo);	//댓글내용
			psmt.setInt(4, parent);		//댓글의 부모brdno
			psmt.setInt(5, brdno);		//댓글 순번 변환용
			psmt.setString(6, userID);	//lastuserno 변환용
			
			psmt.executeUpdate();
			System.out.println(sql); // sql 정상유무
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);		
	}

	public List<BoardReplyVO> getReplyList(int bno, int rp) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		/*reply paging 처리를 위한 변수선언*/
		//page 별 시작 번호와 끝 번호 변수 초기화
		int start = 1+(rp-1)*10;	//page 번호가 3일 경우 (3-1)*10= 20+1 21번 글 부터 시작 
		int end =rp*10;				//page 번호가 3일 경우 3*10  30 번 글 이 끝

		String sql = "select *"
				+ "  from(select @rownum:=@rownum+1 as num, n.* "
				+ "		  from (select tb.brdno as brdno, "
				+ "					   tb.reno as reno, "
				+ "					   (select cu.USERNM  "
				+ "					   	  from com_user cu "
				+ "					  	 where tb.userno = cu.userno "
				+ "							limit 1)as usernm, "
				+ "					   tb.userno as userno, "
				+ "					   tb.rememo as rememo, "
				+ "					   tb.REPARENT as reparent, "
				+ "					   tb.REDEPTH as redepth, "
				+ "					   tb.REORDER as reorder , "
				+ "					   tb.REDATE as regdate "
				+ "				  from tbl_boardreply tb "
				+ "				 where tb.brdno = ? "
				+ "				   and tb.REDELETEFLAG = 'N' "
				+ "				 order by REORDER desc)n "
				+ "		where (@rownum:=0)=0)num	 "
				+ "  where num.num between ? and ? ";

		List<BoardReplyVO> list = new ArrayList<BoardReplyVO>();
		
		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, bno);
			psmt.setInt(2, start);
			psmt.setInt(3, end);
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			while (rs.next()) {
				int brdNo = rs.getInt("brdno");
				int reNo = rs.getInt("reno");
				String user = rs.getString("usernm");
				int userno = rs.getInt("userno");
				String reMemo = rs.getString("rememo");
				int reParent = rs.getInt("reparent");
				int reDepth = rs.getInt("redepth");
				int order = rs.getInt("reorder");
				Date regdate = rs.getDate("regdate");

				BoardReplyVO bv = new BoardReplyVO(brdNo, reNo, user, userno, reMemo, reParent, reDepth, order, regdate);
				list.add(bv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);
		return list;
	}

	public int getReplyCount(int brdno) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = "select count(*) as cnt "
				+ "		from tbl_boardreply tb  "
				+ "	   where tb.BRDNO = ? "
				+ "		 and tb.REDELETEFLAG = 'N' ";

		int cnt = 0;

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, brdno);
			rs = psmt.executeQuery();
			System.out.println(sql); // sql 정상유무

			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JdbcUtil.close(rs);
		JdbcUtil.close(psmt);
		JdbcUtil.close(con);

		return cnt;
	}

	public void deleteReply(int userNo, int reNo) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "update tbl_boardreply tb set tb.REDELETEFLAG = 'Y', "
				+ "							tb.LASTUSERNO  = ?, "
				+ "							tb.LASTDATE = now() "
				+ "	   where tb.RENO = ? ";

		try {
			con = ConnectionProvider.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, userNo);
			psmt.setInt(2, reNo);
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
