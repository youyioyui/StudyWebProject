package com.user.vo;

public class SignUpVO {
	private String userId;
	private String userPw;
	private String userNm;
	public String getUserId() {
		return userId;
	}
	
	public SignUpVO() {
		
	}
	
	public SignUpVO(String userId, String userPw, String userNm) {
		this.userId = userId;
		this.userPw = userPw;
		this.userNm = userNm;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}
