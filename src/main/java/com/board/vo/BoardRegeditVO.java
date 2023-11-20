package com.board.vo;

public class BoardRegeditVO extends BoardListVO {
	private String dept;
	private String userNm;
	
	public BoardRegeditVO() {
	}
	
	public BoardRegeditVO(String brdtitle, String userNm, 
						String brdmemo, String dept, String user) {
		setTitle(brdtitle);
		setUser(user);
		setMemo(brdmemo);
		this.dept = dept;
		this.userNm = userNm;
	}
	
	public BoardRegeditVO(int brdno, String brdTitle, String userNm, String brdMemo, String dept, String userId) {
		setBrdno(brdno);
		setTitle(brdTitle);
		setUser(userId);
		setMemo(brdMemo);
		this.dept = dept;
		this.userNm = userNm;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}
