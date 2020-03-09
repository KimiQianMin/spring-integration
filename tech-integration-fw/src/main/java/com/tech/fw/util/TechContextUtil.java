package com.tech.fw.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import com.tech.fw.domain.TechContext;

@Component
public class TechContextUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionRepository repository;

	public TechContext getTechContext(String cookie) {
		String sessionId = StringUtils.substringAfter(cookie, "SESSION=");
		logger.info("sessionId - {}", sessionId);
		Session session = repository.getSession(sessionId);
		return new TechContext(session.getAttribute("userName"));
	}

}
