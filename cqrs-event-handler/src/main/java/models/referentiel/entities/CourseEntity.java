package models.referentiel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity
{
    @Id private UUID id;
    @Column(name = "type")
    private String type;
    @Column(name = "startdatetime")
    private LocalDateTime startDateTime;
    @Column(name = "enddatetime")
    private LocalDateTime endDateTime;
    @Column(name = "nbmaxparticipant")
    private int nbMaxParticipant;
    @Column(name = "alive")
    private boolean alive;
}
