package com.psa.pnx.spring.cloud.entity;

import java.util.Date;

public class BerthingApplication {

	private Long id;
	private String serverPort;
	private String terminal;
	private Date berthingTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Date getBerthingTime() {
		return berthingTime;
	}

	public void setBerthingTime(Date berthingTime) {
		this.berthingTime = berthingTime;
	}

	@Override
	public String toString() {
		return "BerthingApplication [id=" + id + ", serverPort=" + serverPort + ", terminal=" + terminal
				+ ", berthingTime=" + berthingTime + "]";
	}

}
