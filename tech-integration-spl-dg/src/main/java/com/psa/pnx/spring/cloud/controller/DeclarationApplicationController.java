package com.psa.pnx.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.psa.pnx.spring.cloud.entity.Declaration;
import com.psa.pnx.spring.cloud.service.DeclarationService;

@RestController
public class DeclarationApplicationController {

	@Autowired
	private DeclarationService declarationService;

	@GetMapping("/spl/dg/declaration/{id}")
	public Declaration findById(@PathVariable Integer id) {
		return declarationService.getDeclaration(id);
	}

}
