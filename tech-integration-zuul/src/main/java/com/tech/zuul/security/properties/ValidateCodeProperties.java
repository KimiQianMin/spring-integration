package com.tech.zuul.security.properties;

public class ValidateCodeProperties {

	private ImageCodeProperties imageCode;

	private SmsCodeProperties smsCode;

	public ImageCodeProperties getImageCode() {
		return imageCode;
	}

	public void setImageCode(ImageCodeProperties imageCode) {
		this.imageCode = imageCode;
	}

	public SmsCodeProperties getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(SmsCodeProperties smsCode) {
		this.smsCode = smsCode;
	}

}
