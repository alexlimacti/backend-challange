package com.invillia.acme.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class StoreResource {
	
	@GetMapping
	public String teste() {
		return "rest ok";
	}

}
