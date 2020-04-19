package com.tech.zuul.security.properties;

public class SmsCodeProperties {

	private int length = 6;
	private int expireIn = 60;
	private String codeUrl = "/code/image";
	private String filterUrls = "/authentication/form";

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getFilterUrls() {
		return filterUrls;
	}

	public void setFilterUrls(String filterUrls) {
		this.filterUrls = filterUrls;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

}
