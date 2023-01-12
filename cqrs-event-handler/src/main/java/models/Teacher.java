package models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Teacher
{
    private UUID teacherId;
    private String firstName;
    private String lastname;
}
