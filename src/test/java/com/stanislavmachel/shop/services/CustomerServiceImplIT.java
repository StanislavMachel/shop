package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.mappers.CustomerMapper;
import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.bootstrap.Bootstrap;
import com.stanislavmachel.shop.domain.Customer;
import com.stanislavmachel.shop.repositories.CategoryRepository;
import com.stanislavmachel.shop.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	public CustomerRepository customerRepository;

	private CustomerService customerService;

	@Before
	public void setUp() {

		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);

		bootstrap.run();

		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}

	@Test
	public void getAll() {
		System.out.println("Customers in db: " + customerRepository.count());

		assertEquals(3, customerService.getAll().size());
	}

	@Test
	public void create() {
		System.out.println("Customers in db: " + customerRepository.count());

		CustomerDto customerDto = new CustomerDto();
		String firstName = "Gregor";
		customerDto.setFirstName(firstName);
		String lastName = "Clegane";
		customerDto.setLastName(lastName);

		CustomerDto createdCustomerDto = customerService.create(customerDto);

		assertEquals(4, customerRepository.count());
		Optional<Customer> customer = customerRepository.findById(createdCustomerDto.getId());
		assertTrue(customer.isPresent());
		assertEquals(firstName, customer.get().getFirstName());
		assertEquals(lastName, customer.get().getLastName());
	}


	@Test
	public void update() {
		System.out.println("Customers in db: " + customerRepository.count());

		String newFirstname = "Changed firstname";
		String newLastname = "Changed lastname";


	}
}
