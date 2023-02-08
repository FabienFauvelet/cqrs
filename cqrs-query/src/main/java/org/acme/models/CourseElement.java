package org.acme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonSerialize
public class CourseElement
{
    private String id;
    private String type;
    private int nbMaxParticipant;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private List<String> resources;
    private List<String> customers;
    private String teacherFirstName;
    private String teacherLastName;
    private String teacherId;
    private boolean alive;

    public CourseElement(String id, String type, int nbMaxParticipant, Date startDate, Date endDate, List<String> resources, List<String> customers, String teacherFirstName, String teacherLastName, String teacherId, boolean alive)
    {
        this.id = id;
        this.type = type;
        this.nbMaxParticipant = nbMaxParticipant;
        this.startDate = convertToLocalDateViaInstant(startDate);
        this.endDate = convertToLocalDateViaInstant(endDate);
        this.resources = resources;
        this.customers = customers;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.teacherId = teacherId;
        this.alive = alive;
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
