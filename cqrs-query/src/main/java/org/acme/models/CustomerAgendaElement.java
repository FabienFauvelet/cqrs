package org.acme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@JsonSerialize
public class CustomerAgendaElement
{
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public CustomerAgendaElement(){}

    public CustomerAgendaElement(String name, LocalDateTime startDate, LocalDateTime endDate)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CustomerAgendaElement(String name, Date startDate, Date endDate)
    {
        this.name = name;
        this.startDate = LocalDateTime.parse(startDate.toString());
        this.endDate = LocalDateTime.parse(endDate.toString());
    }

    public CustomerAgendaElement(String name, String startDate, String endDate)
    {
        this.name = name;
        this.startDate = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.endDate = LocalDateTime.parse(endDate);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate)
    {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate)
    {
        this.endDate = endDate;
    }
}
