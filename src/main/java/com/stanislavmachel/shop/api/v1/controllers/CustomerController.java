package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.api.v1.model.CustomerListDto;
import com.stanislavmachel.shop.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<CustomerListDto> getAll() {
		return new ResponseEntity<>(new CustomerListDto(customerService.getAll()), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<CustomerDto> getById(@PathVariable UUID id) {
		try {
			return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto) {
		return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
	}
}
