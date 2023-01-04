package models;

import java.util.UUID;

public class Teacher
{
    private UUID teacherId;
    private String firstName;
    private String lastname;


    public UUID getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
}
