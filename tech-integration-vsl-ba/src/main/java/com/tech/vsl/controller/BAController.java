package com.tech.vsl.controller;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.vsl.entity.BABerthingApplication;

@RestController
@RequestMapping("/vsl/ba")
public class BAController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@GetMapping("/{id}")
	public BABerthingApplication findById(@PathVariable BigInteger id, HttpServletRequest request,
			HttpSession session) {

		if (id.longValue() == 0) {
			throw new RuntimeException();
		}

		BABerthingApplication ba = new BABerthingApplication();
		ba.setId(id);
		ba.setTerminal("SG_TERMAIL_" + id);
		ba.setBerthingTime(new Date());
		ba.setServerPort(this.serverPort);

		logger.info("BAControler sessionId >>>" + request.getHeader("sessionId"));
		logger.info("BAControler userName >>>" + request.getHeader("userName"));

		String sessionId = request.getHeader("sessionId");
		String userName = (String) redisTemplate.opsForValue().get(sessionId);

		logger.info("sessionId >>>" + sessionId);
		logger.info("userName >>>" + userName);

		return ba;
	}

}
