package com.stanislavmachel.shop.api.v1.mappers;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.domain.Category;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.Assert.*;

public class CategoryMapperTest {

	@Test
	public void categotyToCategoryDto() {
		UUID id = UUID.randomUUID();
		String name = "Fruits";

		Category category = new Category();
		category.setId(id);
		category.setName(name);

		CategoryDto categoryDto = CategoryMapper.INSTANCE.categotyToCategoryDto(category);

		assertNotNull(categoryDto);
		assertEquals(categoryDto.getId(), id);
		assertEquals(category.getName(), name);
	}
}