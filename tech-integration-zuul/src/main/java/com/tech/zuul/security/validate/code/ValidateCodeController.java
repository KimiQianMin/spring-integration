package com.tech.zuul.security.validate.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.tech.zuul.ZuulConstants;

@RestController
public class ValidateCodeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	@GetMapping(ZuulConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			throws Exception {
		logger.info("createCode is triggerring ...");
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}

}
