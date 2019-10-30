package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.mappers.CategoryMapper;
import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<CategoryDto> getAll() {
		return categoryRepository.findAll()
				.stream()
				.map(categoryMapper::categotyToCategoryDto)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto getByName(String name) {
		return categoryMapper.categotyToCategoryDto(categoryRepository.findByName(name));
	}
}
