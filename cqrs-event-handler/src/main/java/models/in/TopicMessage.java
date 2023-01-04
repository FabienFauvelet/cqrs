package models.in;

import facade.InsertionResource;

public abstract class TopicMessage {

    private EventType messageType;
    private Object body;

    public TopicMessage()
    {
        messageType = getMessageType();
    }

    public abstract EventType getMessageType();

    protected void setBody(Object body)
    {
        this.body = body;
    }

    protected void setMessageType(EventType eventType)
    {
        this.messageType = eventType;
    }

    protected Object getBody()
    {
        return this.body;
    }

    public abstract void insertObject(InsertionResource resource);
}
