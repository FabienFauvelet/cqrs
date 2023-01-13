package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.in.CustomerAddress;
import models.referentiel.entities.CustomerEntity;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Customer
{
    private UUID id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private CustomerAddress address;

    public CustomerEntity toEntity()
    {
        return new CustomerEntity(this.id,this.firstName,this.lastName,this.birthdate,this.address.toString());
    }
}
