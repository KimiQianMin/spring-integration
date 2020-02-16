package com.psa.pnx.spring.cloud.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.psa.pnx.spring.cloud.entity.BerthingApplication;

@RestController
public class BerthingApplicationController {

	@Value("${server.port}")
	private String serverPort;

	@GetMapping("/vsl/ba/{id}")
	public BerthingApplication findById(@PathVariable Long id) {

		if (id == 0) {
			throw new RuntimeException();
		}
		BerthingApplication ba = new BerthingApplication();
		ba.setId(id);
		ba.setTerminal("SG_TERMAIL_" + id);
		ba.setBerthingTime(new Date());

		ba.setServerPort(this.serverPort);

		// String.format("The response is from <b>%s</b> | %s", this.serverPort,
		// ba.toString());
		return ba;

	}
}
