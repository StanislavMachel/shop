package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
	List<CustomerDto> getAll();

	CustomerDto getById(UUID id);

	CustomerDto create(CustomerDto customerDto);

	CustomerDto update(UUID id, CustomerDto customerDto);

	CustomerDto patch(UUID id, CustomerDto customerDto);
}
