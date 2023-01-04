package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.model.Customer;
import org.acme.out.postgres.entity.CustomerEntity;
import org.acme.out.mapper.CustomerMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity> {
    @Inject
    CustomerMapper customerMapper;
    @Transactional
    public Customer createCustomer(Customer customer){
        CustomerEntity customerEntity =  customerMapper.toCustomerEntity(customer);
        persistAndFlush(customerEntity);
        return customerMapper.toCustomer(customerEntity);
    }

    public boolean exists(UUID eventId){
        return find("id",eventId).count()>0;
    }
    @Transactional
    public void updateCustomer(Customer customer) {
        CustomerEntity customerEntity =  customerMapper.toCustomerEntity(customer);
        persistAndFlush(customerEntity);
    }
    @Transactional
    public void delete(UUID customerId) {
        delete("id",customerId);
    }
}
