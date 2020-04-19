/**
 * 
 */
package com.tech.zuul.security.validate.code;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ValidateCodeProcessorHolder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		logger.info("validate code name - {}", name);
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);

		Assert.notNull(processor, String.format("validate code type %s is invald", type));

		return processor;
	}

}
