package models.referentiel.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity
{
    @Id private UUID id;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column (name = "address")
    private String address;
}
