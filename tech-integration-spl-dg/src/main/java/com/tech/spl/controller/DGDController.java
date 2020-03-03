package com.tech.spl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.spl.entity.DGDDeclaration;
import com.tech.spl.service.DGDDeclarationService;

@RestController
@RequestMapping("/spl/dg")
public class DGDController {

	@Autowired
	private DGDDeclarationService declarationService;

	@GetMapping("/declaration/{id}")
	public DGDDeclaration findById(@RequestHeader("Cookie") String cookie, @PathVariable Integer id) {
		return declarationService.getDeclaration(cookie, id);
	}

}
