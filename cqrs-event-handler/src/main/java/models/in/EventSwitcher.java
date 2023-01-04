package models.in;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import static models.in.EventType.*;

@Getter
@Setter
@JsonDeserialize
public class EventSwitcher {

    private EventType messageType;
    private Object body; //LinkedHashMap

    public EventSwitcher(){}

    public EventSwitcher(String eventType, Object body)
    {
        this.messageType = valueOf(eventType);
        this.body = body;
    }

    public TopicMessage toInsertObjectType()
    {
        try
        {
            return switch (messageType) {
                case EVENT_CREATION -> new EventCourseCreation(this.messageType, this.body);
                case TEACHER_ASSIGNATION -> new EventTeacherAssignation(this.messageType,this.body);
                case RESOURCE_RESERVATION -> new EventResourceReservation(this.messageType,this.body);
                case TEACHER_CREATION -> new EventTeacherCreation(this.messageType,this.body);
                case RESOURCE_CREATION -> null;
                case RESOURCE_RELEASE -> null;
                case EVENT_DELETION -> null;
                default -> null;
            };
        }catch(Exception e)
        {
            System.out.println("[ERROR]: Tranformation de l'évenenement " + messageType.toString() + " échouée..");
            e.printStackTrace();
        }
        return null;
    }
}
