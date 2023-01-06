package models.in;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@JsonDeserialize
public class CustomerAddress
{
    private UUID id;
    private String street;
    private String zipCode;
    private String city;

    public String toString()
    {
        return street
                + " " + zipCode
                + " " + city;
    }
}
