package com.tech.zuul.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.zuul.security.SimpleResponse;
import com.tech.zuul.security.properties.LoginType;
import com.tech.zuul.security.properties.SecurityProperties;

@Component
public class TechAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		logger.info("sign in failed");

		if (LoginType.JSON.equals(securityProperties.getLoginType())) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}

	}

}
