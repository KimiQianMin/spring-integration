package com.tech.zuul.security.validate.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.tech.zuul.security.properties.SecurityProperties;
import com.tech.zuul.security.validate.code.ValidateCode;
import com.tech.zuul.security.validate.code.ValidateCodeGenerator;

@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getValidateCode().getSmsCode().getLength());
		return new ValidateCode(code, securityProperties.getValidateCode().getSmsCode().getExpireIn());
	}

}
