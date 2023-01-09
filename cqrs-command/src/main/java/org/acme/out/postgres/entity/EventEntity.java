package org.acme.out.postgres.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@Data
public class EventEntity {
    @Id @GeneratedValue(generator = "UUID")  private UUID id;
    @Column(name = "start_date_time") private LocalDateTime startDateTime;
    @Column(name = "end_date_time") private LocalDateTime endDateTime;

    @ManyToOne private TeacherEntity teacher;
    @JoinTable(
        name="event_resource",
        joinColumns = @JoinColumn(name ="event_id"),
        inverseJoinColumns = @JoinColumn(name ="resource_id")
    )
    @ManyToMany private List<ResourceEntity> reservedResources;
    @Column(name = "nb_max_participant") private int nbMaxParticipant;
    @ManyToMany private List<CustomerEntity> participants;
    @Column(name = "type")  private String type;
}
