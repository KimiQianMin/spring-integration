package com.tech.zuul.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tech.security")
public class SecurityProperties {

	private BrowserProperties browser = new BrowserProperties();

	private LoginType loginType = LoginType.JSON;

	private ValidateCodeProperties validateCode = new ValidateCodeProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public ValidateCodeProperties getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(ValidateCodeProperties validateCode) {
		this.validateCode = validateCode;
	}

}
