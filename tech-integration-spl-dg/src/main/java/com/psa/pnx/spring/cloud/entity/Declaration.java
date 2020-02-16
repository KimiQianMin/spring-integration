package com.psa.pnx.spring.cloud.entity;

import java.util.Date;

public class Declaration {

	private Integer id;
	private String baServerPort;
	private String baTerminal;
	private Date berthingTime;
	private String chemicalName;
	private String serverPort;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBaServerPort() {
		return baServerPort;
	}

	public void setBaServerPort(String baServerPort) {
		this.baServerPort = baServerPort;
	}

	public String getBaTerminal() {
		return baTerminal;
	}

	public void setBaTerminal(String baTerminal) {
		this.baTerminal = baTerminal;
	}

	public Date getBerthingTime() {
		return berthingTime;
	}

	public void setBerthingTime(Date berthingTime) {
		this.berthingTime = berthingTime;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public String toString() {
		return "Declaration [id=" + id + ", baServerPort=" + baServerPort + ", baTerminal=" + baTerminal
				+ ", berthingTime=" + berthingTime + ", chemicalName=" + chemicalName + ", serverPort=" + serverPort
				+ "]";
	}

}
