package org.acme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@JsonSerialize
public class ResourceAgendaElement
{
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private String teacherFullname;

    public ResourceAgendaElement(String id, String name, Date startDate, Date endDate, String teacherFullname)
    {
        this.id = id;
        this.name = name;
        this.startDate = convertToLocalDateViaInstant(startDate);
        this.endDate = convertToLocalDateViaInstant(endDate);
        this.teacherFullname = teacherFullname;
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
