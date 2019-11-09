package com.stanislavmachel.shop.repositories;

import com.stanislavmachel.shop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
