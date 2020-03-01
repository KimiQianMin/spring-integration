package com.tech.zuul.security;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.tech.zuul.security.authentication.TechAuthenticationFailureHandler;
import com.tech.zuul.security.authentication.TechAuthenticationSuccessHandler;
import com.tech.zuul.security.properties.SecurityProperties;
import com.tech.zuul.security.validate.code.ImageCodeGenerator;
import com.tech.zuul.security.validate.code.ValidateCodeFilter;
import com.tech.zuul.security.validate.code.ValidateCodeGenerator;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private TechAuthenticationSuccessHandler techAuthenticationSuccessHandler;

	@Autowired
	private TechAuthenticationFailureHandler techAuthenticationFailureHandler;
	
	@Autowired
	private ValidateCodeFilter validateCodeFilter;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService techUserDetailsService;
	
	@Autowired
	private SessionInformationExpiredStrategy expiredSessionStategy;
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {
		return new ImageCodeGenerator(); 
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		logger.info("securityProperties.getBrowser().toString() - {}", 
				securityProperties.getBrowser().toString());
		
		http
			.addFilterBefore(validateCodeFilter, 
				UsernamePasswordAuthenticationFilter.class)
			.formLogin()
				.loginPage(securityProperties.getBrowser().getInitLoginPage())
				.loginProcessingUrl(securityProperties.getBrowser().getLoginProcessingUrl())
				.successHandler(techAuthenticationSuccessHandler)
				.failureHandler(techAuthenticationFailureHandler)
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(techUserDetailsService)
				.and()
			.sessionManagement()
				.invalidSessionUrl(securityProperties.getSession().getInvalidSessionUrl())
				.maximumSessions(1)
				//.maxSessionsPreventsLogin(true)
				.expiredSessionStrategy(expiredSessionStategy)
				.and()
				.and()
			.authorizeRequests()
				.antMatchers(securityProperties.getBrowser().getInitLoginPage(), 
						securityProperties.getBrowser().getLoginPage(),
						securityProperties.getValidateCode().getImageCode().getImageCodeUrl(),
						securityProperties.getSession().getInvalidSessionUrl()
						)
					.permitAll()
				.anyRequest()
					.authenticated()
					.and()
				//TODO: understand spring csrf
			.csrf()
				.disable()
			;
	
	}

}
