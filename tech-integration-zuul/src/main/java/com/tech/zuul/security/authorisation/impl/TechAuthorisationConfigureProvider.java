package com.tech.zuul.security.authorisation.impl;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.tech.zuul.security.authorisation.AuthorisationConfigurerProvider;

@Component
public class TechAuthorisationConfigureProvider implements AuthorisationConfigurerProvider {

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry express) {
		
		express.antMatchers(
			HttpMethod.GET, "/api-vsl/**").access("hasAuthority('VSL_EXT') or hasAuthority('VSL_TERMOPR')"
		);
		
	}

}
