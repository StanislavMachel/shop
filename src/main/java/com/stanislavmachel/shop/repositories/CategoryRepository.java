package com.stanislavmachel.shop.repositories;

import com.stanislavmachel.shop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

	Category findByName(String name);
}
