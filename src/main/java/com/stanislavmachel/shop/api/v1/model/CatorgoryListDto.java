package com.stanislavmachel.shop.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatorgoryListDto {
	List<CategoryDto> categories;
}
