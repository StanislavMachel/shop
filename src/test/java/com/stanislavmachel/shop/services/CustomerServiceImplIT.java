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
import java.util.UUID;

import static junit.framework.TestCase.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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

		String newFirstname = "New fistname";
		String newLastname = "New lastname";

		Customer originalCustomer = customerRepository.findAll().get(1);

		assertNotNull(originalCustomer);

		UUID id = originalCustomer.getId();

		String originalFirstname = originalCustomer.getFirstName();
		String originalLastname = originalCustomer.getLastName();

		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(newFirstname);
		customerDto.setLastName(newLastname);

		customerService.update(id, customerDto);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);

		assertEquals(newFirstname, updatedCustomer.getFirstName());
		assertEquals(newLastname, updatedCustomer.getLastName());


	}


	@Test
	public void patchCustomerUpdateFirstname() {
		System.out.println("Customers in db: " + customerRepository.count());

		String newFirstname = "New firstname";

		Customer originalCustomer = customerRepository.findAll().get(1);

		assertNotNull(originalCustomer);

		UUID id = originalCustomer.getId();

		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();

		CustomerDto customerDto = new CustomerDto();
		customerDto.setFirstName(newFirstname);


		customerService.patch(id, customerDto);

		Customer updatedCustomer = customerRepository.findById(id).get();


		assertNotNull(updatedCustomer);
		assertEquals(newFirstname, updatedCustomer.getFirstName());
		assertEquals(originalLastName, updatedCustomer.getLastName());

		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
		assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
	}

	@Test
	public void patchCustomerUpdateLastname() {
		System.out.println("Customers in db: " + customerRepository.count());

		String newFirstname = "New firstname";
		String newLastname = "New lastname";

		Customer originalCustomer = customerRepository.findAll().get(1);

		assertNotNull(originalCustomer);

		UUID id = originalCustomer.getId();

		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();


		CustomerDto customerDto = new CustomerDto();
		customerDto.setLastName(newLastname);
		customerDto.setFirstName(newFirstname);

		customerService.patch(id, customerDto);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);

		assertEquals(newFirstname, updatedCustomer.getFirstName());
		assertEquals(newLastname, updatedCustomer.getLastName());

		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
		assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
	}

	@Test
	public void patchCustomerUpdateFirstnameAndLastname() {
		System.out.println("Customers in db: " + customerRepository.count());

		String newLastname = "New lastname";

		Customer originalCustomer = customerRepository.findAll().get(1);

		assertNotNull(originalCustomer);

		UUID id = originalCustomer.getId();

		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();


		CustomerDto customerDto = new CustomerDto();
		customerDto.setLastName(newLastname);

		customerService.patch(id, customerDto);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);

		assertEquals(originalFirstName, updatedCustomer.getFirstName());
		assertEquals(newLastname, updatedCustomer.getLastName());

		assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
		assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
	}
}
