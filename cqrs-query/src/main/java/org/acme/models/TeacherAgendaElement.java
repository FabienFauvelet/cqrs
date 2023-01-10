package org.acme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonSerialize
public class TeacherAgendaElement
{
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private List<String> resources;
    private List<String> customers;

    public TeacherAgendaElement(){}

    public TeacherAgendaElement(String name, LocalDateTime startDate, LocalDateTime endDate)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TeacherAgendaElement(String id, String name, Date startDate, Date endDate, List<String> resources, List<String> customers)
    {
        this.id = id;
        this.name = name;
        this.startDate = convertToLocalDateViaInstant(startDate);
        this.endDate = convertToLocalDateViaInstant(endDate);
        this.resources = resources;
        this.customers = customers;
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
