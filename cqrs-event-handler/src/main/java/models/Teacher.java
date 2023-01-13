package models;

import lombok.Getter;
import lombok.Setter;
import models.referentiel.entities.TeacherEntity;

import java.util.UUID;

@Getter
@Setter
public class Teacher
{
    private UUID teacherId;
    private String firstName;
    private String lastname;

    public TeacherEntity toEntity()
    {
        return new TeacherEntity(teacherId,firstName,lastname);
    }
}
