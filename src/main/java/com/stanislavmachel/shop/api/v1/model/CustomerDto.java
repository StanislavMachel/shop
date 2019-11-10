package com.stanislavmachel.shop.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	private UUID id;
	private String firstName;
	private String lastName;
	private String url;
}
