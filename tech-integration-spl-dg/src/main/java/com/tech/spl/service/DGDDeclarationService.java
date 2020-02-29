package com.tech.spl.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.spl.entity.DGDDeclaration;

@Component
public class DGDDeclarationService {

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private RestTemplate restTemplate;

	private final String URL_VSL_BA = "http://tech-integration-vsl/vsl/ba/";

	public DGDDeclaration getDeclaration(Integer id) {
		DGDDeclaration d = new DGDDeclaration();

		ObjectMapper mapper = new ObjectMapper();
		String json = this.restTemplate.getForObject(URL_VSL_BA + id, String.class);
		System.out.format("json - {}", json);

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

		System.out.format("Declaration - {}", d);

		return d;

	}

}
