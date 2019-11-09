package com.stanislavmachel.shop.services;

import com.stanislavmachel.shop.api.v1.mappers.CategoryMapper;
import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.domain.Category;
import com.stanislavmachel.shop.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryServiceImplTest {

	private CategoryService categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
	}

	@Test
	public void getAll() {
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

		Mockito.when(categoryRepository.findAll()).thenReturn(categories);

		assertEquals(3, categoryService.getAll().size());
	}

	@Test
	public void getByName() {
		UUID id = UUID.randomUUID();
		String name = "Books";
		Category category = new Category();
		category.setId(id);
		category.setName(name);

		Mockito.when(categoryRepository.findByName(anyString())).thenReturn(category);

		CategoryDto categoryDto = categoryService.getByName(name);

		assertEquals(id, categoryDto.getId());
		assertEquals(name, categoryDto.getName());
	}
}