package models.referentiel.entities;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity
{
    @Id private UUID id;

    @Column(name = "teacher_firstname")
    private String firstname;

    @Column(name = "teacher_lastname")
    private String lastname;


}
