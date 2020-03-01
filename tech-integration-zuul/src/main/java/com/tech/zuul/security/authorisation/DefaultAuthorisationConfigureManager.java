package com.tech.zuul.security.authorisation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthorisationConfigureManager implements AuthorisationConfigurerManager {

	@Autowired
	private Set<AuthorisationConfigurerProvider> authorisationConfigurerProviders;

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry express) {
		authorisationConfigurerProviders.forEach(p -> {
			p.config(express);
		});

		express.anyRequest().authenticated();
	}

}
