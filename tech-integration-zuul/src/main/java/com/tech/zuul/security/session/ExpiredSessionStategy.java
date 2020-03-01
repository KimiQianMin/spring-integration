package com.tech.zuul.security.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.zuul.security.SimpleResponse;

@Component
public class ExpiredSessionStategy implements SessionInformationExpiredStrategy {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		event.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
		event.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		event.getResponse().getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("concurrent login")));
	}

}
