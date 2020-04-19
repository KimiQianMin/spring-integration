package com.tech.zuul.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tech.zuul.security.properties.SecurityProperties;

@RestController
public class SecurityController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@GetMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("targetUrl - {}", targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}

		return new SimpleResponse("Authentication is required, redirect to sign in page...");
	}

	@GetMapping("/session/invalid")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse sessionInvalid() {
		logger.info("sessionInvalid() is starting...");
		return new SimpleResponse("Session is invalid");
	}

	@GetMapping("/user/getUser")
	public Object getUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@GetMapping("/user/getPrincipal")
	public Object getUser(Authentication authentication) {
		return authentication.getPrincipal();
	}
}
