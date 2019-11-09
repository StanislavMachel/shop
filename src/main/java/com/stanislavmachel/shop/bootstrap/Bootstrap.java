package com.stanislavmachel.shop.bootstrap;

import com.stanislavmachel.shop.domain.Category;
import com.stanislavmachel.shop.domain.Customer;
import com.stanislavmachel.shop.repositories.CategoryRepository;
import com.stanislavmachel.shop.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) {

		log.info("Bootstrap application starts...");

		loadCategories();
		loadCustomers();

		log.info("Bootstrap application ends.");
	}

	private void loadCategories() {
		log.info("Start load categories...");

		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		log.info("Categories loaded: {}", categoryRepository.count());
	}

	private void loadCustomers() {
		log.info("Start load customers...");

		Customer customer1 = new Customer();
		customer1.setId(UUID.randomUUID());
		customer1.setFirstName("John");
		customer1.setLastName("Snow");

		Customer customer2 = new Customer();
		customer2.setId(UUID.randomUUID());
		customer2.setFirstName("Arya");
		customer2.setLastName("Stark");

		Customer customer3 = new Customer();
		customer3.setId(UUID.randomUUID());
		customer3.setFirstName("Tyrion");
		customer3.setLastName("Lannister");

		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.save(customer3);

		log.info("Customers loaded: {}", customerRepository.count());
	}
}
