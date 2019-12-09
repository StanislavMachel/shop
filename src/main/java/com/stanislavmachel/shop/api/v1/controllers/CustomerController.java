package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.api.v1.model.CustomerListDto;
import com.stanislavmachel.shop.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

	public static final String BASE_URL = "/api/v1/customers";
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
		return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto) {
		return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<CustomerDto> update(@PathVariable UUID id, @RequestBody CustomerDto customerDto) {
		return new ResponseEntity<>(customerService.update(id, customerDto), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	public ResponseEntity<CustomerDto> patch(@PathVariable UUID id, @RequestBody CustomerDto customerDto) {
		return new ResponseEntity<>(customerService.patch(id, customerDto), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {

		customerService.deleteById(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
