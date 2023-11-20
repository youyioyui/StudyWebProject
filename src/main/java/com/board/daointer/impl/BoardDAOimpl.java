package com.board.daointer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.board.daointer.BoardDAOInter;
import com.web.connection.ConnectionProvider;
import com.web.connection.JdbcUtil;

public class BoardDAOimpl implements BoardDAOInter{


	public int getBoardCount_test(String g, String q) {
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
	
	@Override
	public int getBoardCount(String g, String q) {
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
	}

