package org.acme.domain.services;

import org.acme.domain.exception.InconsistentDomainDataException;
import org.acme.domain.model.Customer;
import org.acme.domain.model.Event;
import org.acme.out.messages.Publisher;
import org.acme.out.postgres.repository.CustomerRepository;
import org.acme.out.postgres.repository.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {
    @Inject CustomerRepository customerRepository;
    @Inject EventRepository eventRepository;
    @Inject Publisher publisher;
    public Customer addCustomer(Customer customer) {
        customer = customerRepository.createCustomer(customer);
        publisher.publishCustomerCreation(customer);
        return customer;
    }

    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
        publisher.publishCustomerUpdate(customer);
    }

    public void deleteCustomer(UUID customerId) throws InconsistentDomainDataException {
        if(!customerRepository.exists(customerId)){
            throw new InconsistentDomainDataException("Le client n'existe pas");
        }
        Customer customer = customerRepository.getCustomer(customerId);
        customer.setFirstName("DELETED");
        customer.setLastName("DELETED");
        customer.setAddress(null);
        customerRepository.updateCustomer(customer);
        publisher.publishCustomerUpdate(customer);
        List<Event> enroledEventList = eventRepository.getCustomerEnrollmentsInFutureEvents(customerId);
        enroledEventList.forEach(event -> publisher.publishEnrolmentCancellation(event.getId(),customerId));
    }
}
