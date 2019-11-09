package com.stanislavmachel.shop.api.v1.mappers;

import com.stanislavmachel.shop.api.v1.model.CustomerDto;
import com.stanislavmachel.shop.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	@Mapping(target = "url", source = ".", qualifiedByName = "toUrl")
	CustomerDto customerToCustomerDto(Customer customer);

	@Named("toUrl")
	default String toUrl(Customer customer) {
		return "/api/v1/customers/" + customer.getId();
	}

	Customer customerDtoToCustomer(CustomerDto customerDto);
}
