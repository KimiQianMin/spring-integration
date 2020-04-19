package com.tech.zuul.security.validate.code;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
	
	private static final long serialVersionUID = 3968579545938313639L;

	public ValidateCodeException(String msg, Throwable t) {
		super(msg, t);
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}
}
