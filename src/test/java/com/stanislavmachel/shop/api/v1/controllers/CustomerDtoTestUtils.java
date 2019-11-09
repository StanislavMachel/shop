package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;

import java.util.UUID;

class CustomerDtoTestUtils {
	static CustomerDto getCustomerDto(UUID id, String firstName, String lastName) {
		CustomerDto customerDto = getCustomerDto(firstName, lastName);
		customerDto.setUrl("/api/v1/customers/" + id);
		return customerDto;
	}

	static CustomerDto getCustomerDto(String firstName, String lastName){
		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(firstName);
		customerDto.setLastName(lastName);
		return customerDto;
	}
}
