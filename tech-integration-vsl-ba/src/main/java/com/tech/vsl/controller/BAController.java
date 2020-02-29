package com.tech.vsl.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.vsl.entity.BABerthingApplication;

@RestController
@RequestMapping("/vsl/ba")
public class BAController {

	@Value("${server.port}")
	private String serverPort;

	@GetMapping("/{id}")
	public BABerthingApplication findById(@PathVariable Long id) {

		if (id == 0) {
			throw new RuntimeException();
		}
		BABerthingApplication ba = new BABerthingApplication();
		ba.setId(id);
		ba.setTerminal("SG_TERMAIL_" + id);
		ba.setBerthingTime(new Date());

		ba.setServerPort(this.serverPort);

		// String.format("The response is from <b>%s</b> | %s", this.serverPort,
		// ba.toString());
		return ba;
	}
	
}
