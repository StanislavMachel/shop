package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;

import java.util.List;

public interface CategoryService {

	List<CategoryDto> getAll();

	CategoryDto getByName(String name);
}
