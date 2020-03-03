/**
 * 
 */
package com.tech.zuul.security.validate.code.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.zuul.ZuulConstants;
import com.tech.zuul.security.properties.SecurityProperties;
import com.tech.zuul.security.validate.code.ValidateCodeException;
import com.tech.zuul.security.validate.code.ValidateCodeProcessorHolder;
import com.tech.zuul.security.validate.code.ValidateCodeType;

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	private Map<String, ValidateCodeType> urlMap = new HashMap<>();

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

		//urlMap.put(ZuulConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getValidateCode().getImageCode().getCodeUrl(), ValidateCodeType.IMAGE);

		//urlMap.put(ZuulConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getValidateCode().getSmsCode().getCodeUrl(), ValidateCodeType.SMS);
	}

	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			logger.info("validate request(" + request.getRequestURI() + ") with validate code type >>>" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				logger.info("pass validation");
			} catch (ValidateCodeException exception) {
				logger.info("validation failed");
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}

}
