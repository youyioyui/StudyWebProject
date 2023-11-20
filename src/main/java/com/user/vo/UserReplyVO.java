package com.user.vo;

import java.util.Date;

public class UserReplyVO extends UserListVO {
	int userno;
	int userReno;
	String backmemo;
	int reorder;
	Date regdate;
	
	public UserReplyVO() {
	}
	
	public UserReplyVO(int userno, int userReno, String backmemo, int reorder, Date regdate) {
		this.userno = userno;
		this.userReno = userReno;
		this.backmemo = backmemo;
		this.reorder = reorder;
		this.regdate = regdate;
	}
	public UserReplyVO(String sessionUserNo, int targetUserno, String rememo) {
		setUserNo(userNo);
		this.userno = targetUserno;
		this.backmemo = rememo;
	}

	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public int getUserReno() {
		return userReno;
	}
	public void setUserReno(int userReno) {
		this.userReno = userReno;
	}
	public String getBackmemo() {
		return backmemo;
	}
	public void setBackmemo(String backmemo) {
		this.backmemo = backmemo;
	}
	public int getReorder() {
		return reorder;
	}
	public void setReorder(int reorder) {
		this.reorder = reorder;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
}
