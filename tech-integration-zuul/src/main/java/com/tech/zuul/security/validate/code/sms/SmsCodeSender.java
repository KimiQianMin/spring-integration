package com.tech.zuul.security.validate.code.sms;

public interface SmsCodeSender {

	void send(String mobile, String code);

}
