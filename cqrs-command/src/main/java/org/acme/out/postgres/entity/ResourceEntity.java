package org.acme.out.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceEntity {
    @Id @GeneratedValue(generator = "UUID")  private UUID id;
    @Column(name = "name")
    private String name;
}
