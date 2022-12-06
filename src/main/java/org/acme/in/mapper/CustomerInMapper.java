package org.acme.in.mapper;

import org.acme.domain.model.Customer;
import org.acme.in.dto.CreateCustomerCommand;
import org.acme.in.dto.UpdateCustomerCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerInMapper {
    Customer toCustomer(CreateCustomerCommand createCustomerCommand);

    Customer toCustomer(UpdateCustomerCommand updateCustomerCommand);
}
