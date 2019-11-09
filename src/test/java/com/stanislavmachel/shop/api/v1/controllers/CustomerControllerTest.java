package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void getAll() throws Exception {

		UUID customerDto1Id = UUID.randomUUID();
		String customer1DtoFirstName = "John";
		String customerDto1LastName = "Snow";

		CustomerDto customerDto1 = CustomerDtoTestUtils.getCustomerDto(customerDto1Id, customer1DtoFirstName, customerDto1LastName);

		UUID customerDto2Id = UUID.randomUUID();
		String customerDto2FirstName = "Arya";
		String customerDto2LastName = "Stark";

		CustomerDto customerDto2 = CustomerDtoTestUtils.getCustomerDto(customerDto2Id, customerDto2FirstName, customerDto2LastName);

		when(customerService.getAll()).thenReturn(Arrays.asList(customerDto1, customerDto2));

		mockMvc.perform(get("/api/v1/customers").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.customers[0].firstName", equalTo(customer1DtoFirstName)))
				.andExpect(jsonPath("$.customers[0].lastName", equalTo(customerDto1LastName)))
				.andExpect(jsonPath("$.customers[1].firstName", equalTo(customerDto2FirstName)))
				.andExpect(jsonPath("$.customers[1].lastName", equalTo(customerDto2LastName)));


	}

	@Test
	public void getByIdWhenCustomerExists() throws Exception {
		UUID id = UUID.randomUUID();
		String firstName = "Ned";
		String lastName = "Stark";
		CustomerDto customerDto = CustomerDtoTestUtils.getCustomerDto(id, firstName, lastName);

		when(customerService.getById(id)).thenReturn(customerDto);

		mockMvc.perform(get("/api/v1/customers/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(firstName)))
				.andExpect(jsonPath("$.lastName", equalTo(lastName)));
	}

	@Test
	public void getByIdWhenCustomerNotExist() throws Exception {
		when(customerService.getById(any(UUID.class))).thenThrow(new RuntimeException());

		mockMvc.perform(get("/api/v1/customers/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}