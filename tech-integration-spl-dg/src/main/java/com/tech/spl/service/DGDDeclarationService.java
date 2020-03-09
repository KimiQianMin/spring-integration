package com.tech.spl.service;

import java.io.IOException;
import java.math.BigInteger;
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
import com.tech.fw.util.TechContextUtil;
import com.tech.spl.entity.DGDDeclaration;

@Component
public class DGDDeclarationService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TechContextUtil contextUtil;

	private final String URL_VSL_BA = "http://tech-integration-vsl/vsl/ba/";

	private final String URL_ZUUL_USER_GETPRINCIPAL = "http://tech-integration-zuul/user/getPrincipal";

	public DGDDeclaration getDeclaration(String cookie, Integer id) {

		DGDDeclaration d = new DGDDeclaration();

		ObjectMapper mapper = new ObjectMapper();
		String json = restTemplate.getForObject(URL_VSL_BA + id, String.class);
		logger.info("json - {}", json);

		Map<String, Object> map = null;

		try {
			map = mapper.readValue(json, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		d.setId(new BigInteger("1"));
		d.setChemicalName("Test");
		d.setServerPort(serverPort);

		d.setBaId((BigInteger) map.get("baId"));
		d.setBaServerPort((String) map.get("baServerPort"));
		d.setBaTerminal((String) map.get("baTerminal"));
		d.setBaBerthingTime((Date) map.get("baBerthingTime"));

		logger.info("Declaration - {}", d);

		logger.info(contextUtil.getTechContext(cookie).toString());

		// getPrincipal(cookie);

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
