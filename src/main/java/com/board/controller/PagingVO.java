package com.board.controller;

public class PagingVO {
	String f;
	String q;
	String p;
	int rp;

	public PagingVO() {

	}
	public PagingVO(String f, String q, String p, int rp) {
		this.f = f;
		this.q = q;
		this.p = p;
		this.rp = rp;
	}

	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
}
