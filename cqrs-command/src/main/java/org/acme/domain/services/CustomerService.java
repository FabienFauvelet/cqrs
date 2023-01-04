package org.acme.domain.services;

import org.acme.domain.model.Customer;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    Publisher publisher;
    public void addCustomer(Customer customer) {
        customer = customerRepository.createCustomer(customer);
        // TODO PUBLISH CREATE ADDRESS
        publisher.publishCustomerCreation(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
        // TODO PUBLISH ADDRESS MODIFICATION
        publisher.publishCustomerUpdate(customer);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
        // TODO PUBLISH CUSTOMER DELETION (real deletion ?)
    }
}
