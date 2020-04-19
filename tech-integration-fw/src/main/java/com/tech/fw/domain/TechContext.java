package com.tech.fw.domain;

public class TechContext {

	public TechContext() {
	}

	public TechContext(String userName) {
		this.userName = userName;
	}

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "TechContext [userName=" + userName + "]";
	}

}
