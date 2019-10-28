package com.stanislavmachel.shop.api.v1.controllers;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.api.v1.model.CatorgoryListDto;
import com.stanislavmachel.shop.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<CatorgoryListDto> getAll() {
		return new ResponseEntity<>(new CatorgoryListDto(categoryService.getAll()), HttpStatus.OK);
	}

	@GetMapping("{name}")
	public ResponseEntity<CategoryDto> getByName(@PathVariable String name) {
		return new ResponseEntity<>(categoryService.getByName(name), HttpStatus.OK);
	}

}
