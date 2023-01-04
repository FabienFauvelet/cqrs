package models;

import org.bson.json.JsonObject;

public class CalendarElement {

    private String action;
    private JsonObject body;

    public CalendarElement(String action, JsonObject body)
    {
        this.action = action;
        this.body = body;
    }

    public CalendarElement(String action, String strJson)
    {
        this.action = action;
        this.body = new JsonObject(strJson);
    }


}
