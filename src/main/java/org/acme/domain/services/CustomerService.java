package org.acme.domain.services;

import org.acme.domain.model.Customer;
import org.acme.out.postgres.repository.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;
    public void addCustomer(Customer customer) {
        customerRepository.createCustomer(customer);
    }
}
