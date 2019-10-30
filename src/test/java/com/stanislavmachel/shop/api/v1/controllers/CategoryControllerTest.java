package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.services.CategoryService;
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
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

	@Mock
	CategoryService categoryService;

	@InjectMocks
	CategoryController categoryController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void getAll() throws Exception {
		UUID id1 = UUID.randomUUID();
		String name1 = "Cars";
		CategoryDto categoryDto1 = new CategoryDto();
		categoryDto1.setId(id1);
		categoryDto1.setName(name1);

		UUID id2 = UUID.randomUUID();
		String name2 = "Books";
		CategoryDto categoryDto2 = new CategoryDto();
		categoryDto2.setId(id2);
		categoryDto2.setName(name2);

		when(categoryService.getAll()).thenReturn(Arrays.asList(categoryDto1, categoryDto2));

		mockMvc.perform(get("/api/v1/categories")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)))
				.andExpect(jsonPath("$.categories[0].id", equalTo(id1.toString())))
				.andExpect(jsonPath("$.categories[0].name", equalTo(name1)))
				.andExpect(jsonPath("$.categories[1].id", equalTo(id2.toString())))
				.andExpect(jsonPath("$.categories[1].name", equalTo(name2)));
	}

	@Test
	public void getByName() throws Exception {

		UUID id = UUID.randomUUID();
		String name = "Cars";

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(id);
		categoryDto.setName(name);

		when(categoryService.getByName(anyString())).thenReturn(categoryDto);

		mockMvc.perform(get("/api/v1/categories/cars")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(id.toString())))
				.andExpect(jsonPath("$.name", equalTo(name)));

	}
}