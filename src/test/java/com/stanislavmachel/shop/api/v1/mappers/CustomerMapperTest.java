package com.stanislavmachel.shop.api.v1.mappers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.domain.Customer;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerMapperTest {

	@Test
	public void customerToCustomerDto() {

		UUID id = UUID.randomUUID();
		String firstName = "John";
		String lastName = "Smith";

		Customer customer = new Customer();

		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);

		CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto(customer);

		assertNotNull(customerDto);
		assertEquals(firstName, customerDto.getFirstName());
		assertEquals(lastName, customerDto.getLastName());
		assertEquals("/api/v1/customers/" + id, customerDto.getUrl());
	}

	@Test
	public void customerDtoToCustomer() {

		String firstName = "Ned";
		String lastName = "Stark";
		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(firstName);
		customerDto.setLastName(lastName);

		Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);

		assertNotNull(customer);
		assertEquals(firstName, customer.getFirstName());
		assertEquals(lastName, customer.getLastName());
	}
}
