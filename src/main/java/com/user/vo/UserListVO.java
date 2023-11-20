package com.user.vo;

public class UserListVO {
	int userNo;
	String userId;
	String userNm;
	String userRole;
	String dept;
	

	public UserListVO() {
		
	}
	
	
	
	public UserListVO(String userId, String userNm, String userRole, String dept) {
		this.userId = userId;
		this.userNm = userNm;
		this.userRole = userRole;
		this.dept = dept;
	}

	public UserListVO(int userNo, String userId, String userNm, String userRole, String dept) {
		this.userNo = userNo;
		this.userId = userId;
		this.userNm = userNm;
		this.userRole = userRole;
		this.dept = dept;
	}

	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
}
