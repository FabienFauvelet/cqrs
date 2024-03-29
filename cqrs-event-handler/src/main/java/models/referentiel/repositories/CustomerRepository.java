package models.referentiel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Customer;
import models.referentiel.entities.CustomerEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity>
{
    @Transactional
    public void createCustomer(CustomerEntity customerEntity)
    {
        persistAndFlush(customerEntity);
    }

    @Transactional
    public void deleteCustomer(UUID customerID)
    {
        delete("id",customerID);
    }
}
