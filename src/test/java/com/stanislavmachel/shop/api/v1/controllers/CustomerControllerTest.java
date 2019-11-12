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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {

	private static final String API_V1_CUSTOMERS_URL = "/api/v1/customers/";
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

		mockMvc.perform(get(API_V1_CUSTOMERS_URL + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(firstName)))
				.andExpect(jsonPath("$.lastName", equalTo(lastName)));
	}

	@Test
	public void getByIdWhenCustomerNotExist() throws Exception {
		when(customerService.getById(any(UUID.class))).thenThrow(new RuntimeException());

		mockMvc.perform(get(API_V1_CUSTOMERS_URL + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void create() throws Exception {
		CustomerDto customerDtoToCreate = CustomerDtoTestUtils.getCustomerDto("John", "Snow");

		UUID createdCustomerId = UUID.randomUUID();


		CustomerDto createdCustomerDto = CustomerDtoTestUtils.getCustomerDto(createdCustomerId, customerDtoToCreate.getFirstName(), customerDtoToCreate.getLastName());

		when(customerService.create(customerDtoToCreate)).thenReturn(createdCustomerDto);


		mockMvc.perform(post(API_V1_CUSTOMERS_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(customerDtoToCreate)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", equalTo(customerDtoToCreate.getFirstName())))
				.andExpect(jsonPath("$.lastName", equalTo(customerDtoToCreate.getLastName())))
				.andExpect(jsonPath("$.url", equalTo(API_V1_CUSTOMERS_URL + createdCustomerId)));

	}

	@Test
	public void updateIfCustomerExists() throws Exception {
		UUID id = UUID.randomUUID();
		String newFirstName = "newFirstName";
		String newLastName = "newLastName";

		CustomerDto updatedDto = new CustomerDto();
		updatedDto.setFirstName(newFirstName);
		updatedDto.setLastName(newLastName);

		when(customerService.update(any(UUID.class), any(CustomerDto.class))).thenReturn(updatedDto);

		mockMvc.perform(
				put(API_V1_CUSTOMERS_URL + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(updatedDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(newFirstName)))
				.andExpect(jsonPath("$.lastName", equalTo(newLastName)));

	}

	@Test
	public void updateIfCustomerNotExist() throws Exception {
		when(customerService.update(any(UUID.class), any(CustomerDto.class))).thenThrow(new IllegalArgumentException());

		mockMvc.perform(
				put(API_V1_CUSTOMERS_URL + UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new CustomerDto())))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void patchTest() throws Exception {

		String oldFirstname = "Old firstname";
		String newLastname= "New lastname";


		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(oldFirstname);
		customerDto.setLastName(newLastname);

		when(customerService.patch(any(UUID.class), any(CustomerDto.class))).thenReturn(customerDto);

		CustomerDto customerDtoForUpdate = new CustomerDto();
		customerDtoForUpdate.setLastName(newLastname);

		mockMvc.perform(
				patch(API_V1_CUSTOMERS_URL + UUID.randomUUID()).
						contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(customerDtoForUpdate)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo(oldFirstname)))
				.andExpect(jsonPath("$.lastName", equalTo(newLastname)));
	}

}