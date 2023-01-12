package models.referentiel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Customer;
import models.referentiel.entities.CustomerEntity;

public class CustomerRepository implements PanacheRepository<CustomerEntity>
{

    public void createCustomer(Customer customer)
    {

    }

    public void deleteCustomer(Customer customer)
    {

    }

}
