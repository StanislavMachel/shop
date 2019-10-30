package com.stanislavmachel.shop.api.v1.mappers;

import com.stanislavmachel.shop.api.v1.model.CategoryDto;
import com.stanislavmachel.shop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryDto categotyToCategoryDto(Category category);
}
