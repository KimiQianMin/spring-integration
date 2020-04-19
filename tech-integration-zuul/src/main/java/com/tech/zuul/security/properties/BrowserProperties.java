package com.tech.zuul.security.properties;

import com.tech.zuul.ZuulConstants;

public class BrowserProperties {

	private String initLoginPage = ZuulConstants.URL_LOGIN_PAGE;
	private String loginPage = "tech-signin";
	private String loginProcessingUrl = "/authentication/form";
	private int rememberMeSeconds = 3600;

	public String getInitLoginPage() {
		return initLoginPage;
	}

	public void setInitLoginPage(String initLoginPage) {
		this.initLoginPage = initLoginPage;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}

	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	@Override
	public String toString() {
		return "BrowserProperties [initLoginPage=" + initLoginPage + ", loginPage=" + loginPage
				+ ", loginProcessingUrl=" + loginProcessingUrl + ", rememberMeSeconds=" + rememberMeSeconds + "]";
	}

}
