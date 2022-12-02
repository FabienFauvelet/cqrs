package org.acme.domain.services;

import org.acme.domain.model.Customer;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
}
