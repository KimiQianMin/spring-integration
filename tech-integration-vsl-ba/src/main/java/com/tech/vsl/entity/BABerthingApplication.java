package com.tech.vsl.entity;

import java.math.BigInteger;
import java.util.Date;

public class BABerthingApplication {

	private BigInteger id;
	private String serverPort;
	private String terminal;
	private Date berthingTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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

}
