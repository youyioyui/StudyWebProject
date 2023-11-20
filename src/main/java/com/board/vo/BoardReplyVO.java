package com.board.vo;

import java.util.Date;

public class BoardReplyVO extends BoardListVO{
	int brdNo;
	int reNo;
	int userNo;
	String reMemo;
	int reParent;
	int reDepth;
	int order;
	Date regdate;

	public int getBrdNo() {
		return brdNo;
	}
	public void setBrdNo(int brdNo) {
		this.brdNo = brdNo;
	}
	public int getReNo() {
		return reNo;
	}
	public void setReNo(int reNo) {
		this.reNo = reNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getReMemo() {
		return reMemo;
	}
	public void setReMemo(String reMemo) {
		this.reMemo = reMemo;
	}
	public int getReParent() {
		return reParent;
	}
	public void setReParent(int reParent) {
		this.reParent = reParent;
	}
	public int getReDepth() {
		return reDepth;
	}
	public void setReDepth(int reDepth) {
		this.reDepth = reDepth;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public BoardReplyVO(String userID, int brdNo, String reMemo) {
		setUser(userID);
		this.brdNo = brdNo;
		this.reMemo = reMemo;
	}
	
	public BoardReplyVO(int brdNo, int reNo, String user, int userNo,  String reMemo, int reParent, int reDepth, int order,
			Date regdate) {
		this.brdNo = brdNo;
		this.reNo = reNo;
		setUser(user);
		this.userNo = userNo;
		this.reMemo = reMemo;
		this.reParent = reParent;
		this.reDepth = reDepth;
		this.order = order;
		this.regdate = regdate;
	}
	
	public BoardReplyVO() {
	}
}
