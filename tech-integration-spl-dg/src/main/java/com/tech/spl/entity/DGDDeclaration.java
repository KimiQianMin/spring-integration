package com.tech.spl.entity;

import java.math.BigInteger;
import java.util.Date;

public class DGDDeclaration {

	private BigInteger id;
	private String chemicalName;
	private String serverPort;

	private BigInteger baId;
	private String baServerPort;
	private String baTerminal;
	private Date baBerthingTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public BigInteger getBaId() {
		return baId;
	}

	public void setBaId(BigInteger baId) {
		this.baId = baId;
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

	public Date getBaBerthingTime() {
		return baBerthingTime;
	}

	public void setBaBerthingTime(Date baBerthingTime) {
		this.baBerthingTime = baBerthingTime;
	}

	@Override
	public String toString() {
		return "DGDDeclaration [id=" + id + ", chemicalName=" + chemicalName + ", serverPort=" + serverPort + ", baId="
				+ baId + ", baServerPort=" + baServerPort + ", baTerminal=" + baTerminal + ", baBerthingTime="
				+ baBerthingTime + "]";
	}

}
