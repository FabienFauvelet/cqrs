package org.acme.out.postgres.entity;

import lombok.Data;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity {
    @Id @GeneratedValue(generator = "UUID")  private UUID id;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
