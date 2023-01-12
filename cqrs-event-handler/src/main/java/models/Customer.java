package models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Customer
{
    private UUID id;
    private String firstName;
    private String lastName;

}
