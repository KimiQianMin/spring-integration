package com.tech.spl.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.spl.entity.DGDDeclaration;

@Component
public class DGDDeclarationService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private RestTemplate restTemplate;

	private final String URL_VSL_BA = "http://tech-integration-vsl/vsl/ba/";

	private final String URL_ZUUL_USER_GETPRINCIPAL = "http://tech-integration-zuul/user/getPrincipal";

	public DGDDeclaration getDeclaration(String cookie, Integer id) {
		DGDDeclaration d = new DGDDeclaration();

		ObjectMapper mapper = new ObjectMapper();
		String json = restTemplate.getForObject(URL_VSL_BA + id, String.class);
		logger.info("json - {}", json);

		try {
			Map<String, Object> map = mapper.readValue(json, Map.class);

			d.setId((Integer) map.get("id"));
			d.setBaTerminal("SG_TERMAIL_" + map.get("id"));
			d.setBerthingTime(new Date((Long) map.get("berthingTime")));
			d.setBaServerPort((String) map.get("serverPort"));

			d.setChemicalName("Test");
			d.setServerPort(serverPort);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("Declaration - {}", d);

		getPrincipal(cookie);

		return d;
	}

	private void getPrincipal(String cookie) {
		logger.info("getPrincipal() is starting ...");
		logger.info("cookie - {}", cookie);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", cookie);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<Map> response = restTemplate.exchange(URL_ZUUL_USER_GETPRINCIPAL, HttpMethod.GET, request,
				Map.class);
		logger.info("response.getBody() - {}", response.getBody());
	}

}
