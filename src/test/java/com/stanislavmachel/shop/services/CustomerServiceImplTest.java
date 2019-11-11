package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.mappers.CustomerMapper;
import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.domain.Customer;
import com.stanislavmachel.shop.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

	private static final String API_V1_CUSTOMERS_URL = "/api/v1/customers/";

	@Mock
	CustomerRepository customerRepository;

	private CustomerService customerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}

	@Test
	public void getAll() {

		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

		when(customerRepository.findAll()).thenReturn(customers);

		assertEquals(3, customerService.getAll().size());
	}


	@Test
	public void getCustomerById() {
		UUID id = UUID.randomUUID();
		String firstName = "John";
		String lastName = "Snow";
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);


		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

		CustomerDto customerDto = customerService.getById(id);

		assertNotNull(customerDto);
		assertEquals(firstName, customerDto.getFirstName());
		assertEquals(lastName, customerDto.getLastName());
		assertEquals(API_V1_CUSTOMERS_URL + id, customerDto.getUrl());
	}

	@Test
	public void create() {

		UUID id = UUID.randomUUID();
		String firstName = "John";
		String lastName = "Snow";
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);


		CustomerDto customerDtoToCreate = new CustomerDto();
		customerDtoToCreate.setFirstName(firstName);
		customerDtoToCreate.setLastName(lastName);


		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerDto createdCustomerDto = customerService.create(customerDtoToCreate);

		assertEquals(firstName, createdCustomerDto.getFirstName());
		assertEquals(lastName, createdCustomerDto.getLastName());
		assertEquals(API_V1_CUSTOMERS_URL + id, createdCustomerDto.getUrl());

	}

	private void fakeCustomers() {
		UUID customer1Id = UUID.randomUUID();
		Customer customer1 = new Customer();
		customer1.setId(customer1Id);
		customer1.setFirstName("John");
		customer1.setLastName("Snow");

		UUID customer2Id = UUID.randomUUID();
		Customer customer2 = new Customer();
		customer2.setId(customer2Id);
		customer2.setFirstName("Arya");
		customer2.setLastName("Stark");

		UUID customer3Id = UUID.randomUUID();
		Customer customer3 = new Customer();
		customer3.setId(customer3Id);
		customer3.setFirstName("Tyrion");
		customer3.setLastName("Lannister");
	}

	@Test
	public void updateExistingCustomer() {
		UUID id = UUID.randomUUID();
		String newFirsName = "NewFirsName";
		String newLastName = "NewLastName";

		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName("OldFirsName");
		customer.setLastName("OldLastName");

		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

		Customer updatedCustomer = new Customer();
		updatedCustomer.setId(id);


		updatedCustomer.setFirstName(newFirsName);

		updatedCustomer.setLastName(newLastName);

		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(newFirsName);
		customerDto.setLastName(newLastName);

		CustomerDto updatedCustomerDto = customerService.update(id, customerDto);

		assertEquals(newFirsName, updatedCustomerDto.getFirstName());
		assertEquals(newLastName, updatedCustomerDto.getLastName());
		assertEquals(API_V1_CUSTOMERS_URL + id, updatedCustomerDto.getUrl());

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateIfCustomerNotExist() {
		when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
		customerService.update(UUID.randomUUID(), new CustomerDto());
	}


	@Test
	public void patch(){
		UUID id = UUID.randomUUID();
		String oldFirsName = "Old firstname";
		String oldLastName = "Old lastname";
		String newFirstName = "New firstname";

		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(oldFirsName);
		customer.setLastName(oldLastName);

		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

		customer.setFirstName(newFirstName);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(newFirstName);
		customerDto.setLastName(oldLastName);

		CustomerDto newCustomerDto = customerService.patch(id, customerDto);
	}
}