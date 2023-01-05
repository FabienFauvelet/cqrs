package org.acme.out.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(name = "street")
    private String street;
    @Column(name = "zipCode")
    private String zipCode;
    @Column(name = "city")
    private String city;
}
