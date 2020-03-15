package com.tech.zuul.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TechUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		logger.info("username - {}", username);
//		return new User(username, passwordEncoder.encode("123456"), 
//				true, true, true, true,
//				AuthorityUtils.commaSeparatedStringToAuthorityList("VSL_EXT"));
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Sign in username - " + username);
		return buildUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		logger.info("Sign in userId - " + userId);
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {
		String password = passwordEncoder.encode("123456");
		logger.info("password - {}", password);
		return new SocialUser(userId, password, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("VSL_EXT"));
	}

}
