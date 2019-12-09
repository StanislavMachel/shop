package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.api.v1.model.CatorgoryListDto;
import com.stanislavmachel.shop.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	public static final String BASE_URL = "/api/v1/categories";
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CatorgoryListDto getAll() {
		return new CatorgoryListDto(categoryService.getAll());
	}

	@GetMapping("{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDto getByName(@PathVariable String name) {
		return categoryService.getByName(name);
	}

}
