package org.acme.in.mapper;

import org.acme.domain.model.Address;
import org.acme.domain.model.Customer;
import org.acme.in.dto.customer.CreateCustomerCommand;
import org.acme.in.dto.customer.UpdateCustomerCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerInMapper {
    Customer toCustomer(CreateCustomerCommand createCustomerCommand);

    Customer toCustomer(UpdateCustomerCommand updateCustomerCommand);
    Address toAddress(CreateCustomerCommand.Address address);
}
