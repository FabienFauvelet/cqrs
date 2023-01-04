package org.acme.out.mapper;

import org.acme.domain.model.Customer;
import org.acme.out.postgres.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {
    CustomerEntity toCustomerEntity(Customer customer);
    Customer toCustomer(CustomerEntity customerEntity);
}
