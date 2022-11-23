package org.acme.in.mapper;

import org.acme.domain.model.Customer;
import org.acme.in.dto.CreateCustomerQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerInMapper {
    Customer toCustomer(CreateCustomerQuery createCustomerQuery);
}
