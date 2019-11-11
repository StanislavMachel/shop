package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.mappers.CustomerMapper;
import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDto> getAll() {
		return customerRepository.findAll()
				.stream()
				.map(customerMapper::customerToCustomerDto)
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDto getById(UUID id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDto)
				.orElseThrow(() -> new RuntimeException("Customer with id: " + id + " not found"));
	}

	@Override
	public CustomerDto create(CustomerDto customerDto) {
		return customerMapper.customerToCustomerDto(
				customerRepository.save(
						customerMapper.customerDtoToCustomer(customerDto)));
	}

	@Override
	public CustomerDto update(UUID id, CustomerDto customerDto) {
		return customerRepository.findById(id)
				.map(customer -> {
					customer.setFirstName(customerDto.getFirstName());
					customer.setLastName(customerDto.getLastName());
					return customerMapper.customerToCustomerDto(customerRepository.save(customer));
				}).orElseThrow(() -> new IllegalArgumentException("Customer with id: " + id + " not exist"));
	}
}
