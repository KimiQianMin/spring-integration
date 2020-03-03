package com.tech.zuul.security.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.tech.zuul.security.validate.code.ValidateCode;
import com.tech.zuul.security.validate.code.ValidateCodeException;
import com.tech.zuul.security.validate.code.ValidateCodeGenerator;
import com.tech.zuul.security.validate.code.ValidateCodeProcessor;
import com.tech.zuul.security.validate.code.ValidateCodeRepository;
import com.tech.zuul.security.validate.code.ValidateCodeType;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Autowired
	private ValidateCodeRepository validateCodeRepository;

	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("ValidateCodeGenerator '" + generatorName + "' not existing");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	private void save(ServletWebRequest request, C validateCode) {
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));
	}

	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		ValidateCodeType codeType = getValidateCodeType(request);

		C codeInSession = (C) validateCodeRepository.get(request, codeType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					codeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("failed to get validate code");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(codeType + "validate code can't be null");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(codeType + "validate code not existing");
		}

		if (codeInSession.isExpired()) {
			validateCodeRepository.remove(request, codeType);
			throw new ValidateCodeException(codeType + "validate code was expired");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(codeType + "validate code not matched");
		}

		validateCodeRepository.remove(request, codeType);

	}

}
