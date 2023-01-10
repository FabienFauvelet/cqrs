package org.acme.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
public class CustomerAgendaElementString
{
    private String name;
    private String startDate;
    private String endDate;

    public CustomerAgendaElementString(String name, String startDate, String endDate)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
