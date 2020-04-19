package com.tech.zuul.security.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSmsCodeSender implements SmsCodeSender {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void send(String mobile, String code) {
		logger.info("validate code [{}] was sent to mobile #{}", code, mobile);
	}

}
