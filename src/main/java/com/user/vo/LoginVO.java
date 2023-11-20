package com.user.vo;

public class LoginVO {
	
	private String pid;
	private String ppass;
	
	public LoginVO() {
	}
	
	public LoginVO(String pid, String ppass) {
		this.pid = pid;
		this.ppass = ppass;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPpass() {
		return ppass;
	}
	public void setPpass(String ppass) {
		this.ppass = ppass;
	}
}
