package com.tech.zuul.security.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.zuul.ZuulConstants;
import com.tech.zuul.security.properties.SecurityProperties;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	private Set<String> filterUrls = new HashSet<>();

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
				securityProperties.getValidateCode().getImageCode().getFilterUrls(), ",");
		for (String url : urls) {
			filterUrls.add(url);
		}
		logger.info("validate code filterUrls - {}", filterUrls);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("request.getRequestURI() - {}", request.getRequestURI());
		logger.info("request.getMethod() - {}", request.getMethod());

		boolean action = false;
		for (String url : filterUrls) {
			if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
				break;
			}
		}

		if (action) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException ex) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {

		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
				ZuulConstants.VALIDATE_CODE_IMAGE_CODE_SESSION_KEY);

		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

		logger.info("codeInSession - {}", codeInSession);
		logger.info("codeInRequest - {}", codeInRequest);

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("Code is not existing");
		}

		if (codeInSession.isExpired()) {
			sessionStrategy.removeAttribute(request, ZuulConstants.VALIDATE_CODE_IMAGE_CODE_SESSION_KEY);
			throw new ValidateCodeException("Code is explired");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("Code is not matched");
		}

		sessionStrategy.removeAttribute(request, ZuulConstants.VALIDATE_CODE_IMAGE_CODE_SESSION_KEY);
	}

}
