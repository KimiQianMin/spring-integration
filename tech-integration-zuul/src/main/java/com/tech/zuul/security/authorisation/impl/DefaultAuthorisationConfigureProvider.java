package com.tech.zuul.security.authorisation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.tech.zuul.security.authorisation.AuthorisationConfigurerProvider;
import com.tech.zuul.security.properties.SecurityProperties;

@Component
public class DefaultAuthorisationConfigureProvider implements AuthorisationConfigurerProvider {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry express) {
		
		express.antMatchers(
			securityProperties.getBrowser().getInitLoginPage(),
			securityProperties.getBrowser().getLoginPage(),
			securityProperties.getValidateCode().getImageCode().getCodeUrl(),
			securityProperties.getValidateCode().getSmsCode().getCodeUrl(),
			securityProperties.getSession().getInvalidSessionUrl()
		).permitAll();
		
	}

}
