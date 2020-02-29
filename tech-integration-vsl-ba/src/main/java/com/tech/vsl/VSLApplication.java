package com.tech.vsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VSLApplication {

	public static void main(String[] args) {
		SpringApplication.run(VSLApplication.class, args);
	}
}
