package models;

import org.bson.json.JsonObject;

public class Course
{

    private String action;
    private JsonObject body;

    public Course(String action, JsonObject body)
    {
        this.action = action;
        this.body = body;
    }

    public Course(String action, String strJson)
    {
        this.action = action;
        this.body = new JsonObject(strJson);
    }


}
