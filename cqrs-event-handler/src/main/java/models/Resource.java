package models;

import java.util.UUID;

public class Resource
{
    private UUID id;
    private String name;

    public Resource(UUID id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Resource(){}

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
