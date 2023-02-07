package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.referentiel.entities.CourseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Course
{
    private UUID id;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int nbMaxParticipant;
    private boolean alive;

    public CourseEntity toEntity()
    {
        return new CourseEntity(id,type,startDateTime,endDateTime,nbMaxParticipant,alive);
    }
}
