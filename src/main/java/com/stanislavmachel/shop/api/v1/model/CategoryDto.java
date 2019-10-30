package com.stanislavmachel.shop.api.v1.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {

	private UUID id;
	private String name;
}