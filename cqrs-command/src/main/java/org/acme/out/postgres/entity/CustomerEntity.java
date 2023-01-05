package org.acme.out.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.Address;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id @GeneratedValue(generator = "UUID")  private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthdate")
    private LocalDate birthDate;
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="address_id")
    private AddressEntity address;
}
