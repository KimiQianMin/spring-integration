package com.tech.zuul.filter;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class SessionSavingZuulPreFilter extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		RequestContext ctx = RequestContext.getCurrentContext();

		logger.info("zuul userName >>>" + authentication.getName());

		HttpSession httpSession = ctx.getRequest().getSession();
		httpSession.setAttribute("userName", authentication.getName());

		ctx.addZuulRequestHeader("sessionId", httpSession.getId());
		ctx.addZuulRequestHeader("userName", authentication.getName());

		logger.info("zuul sessionId >>>" + httpSession.getId());
		logger.info("zuul userName >>>" + authentication.getName());
		
		// server.session.timeout = 600, follow the value configured inside application.yml
		redisTemplate.opsForValue().set(httpSession.getId(), authentication.getName(), 600, TimeUnit.SECONDS);

		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}
}