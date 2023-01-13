package models.in;
import com.aayushatharva.brotli4j.common.annotations.Local;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static models.in.EventType.*;

@Getter
@Setter
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSwitcher {

    private EventType messageType;
    private LocalDateTime localDateTime;
    private Object body; //LinkedHashMap

    public EventSwitcher(){}

    public EventSwitcher(String eventType, LocalDateTime localDateTime, Object body)
    {
        this.messageType = valueOf(eventType);
        this.localDateTime = localDateTime;
        this.body = body;
    }

    public TopicMessage toInsertObjectType()
    {
        try
        {
            return switch (messageType) {
                case EVENT_CREATION -> new EventCourseCreation(this.messageType, this.body);
                case CUSTOMER_CREATION -> new EventCustomerCreation(this.messageType,this.body);
                case TEACHER_CREATION -> new EventTeacherCreation(this.messageType,this.body);
                case RESOURCE_CREATION -> new EventResourceCreation(this.messageType,this.body);
                case TEACHER_ASSIGNATION -> new EventTeacherAssignation(this.messageType,this.body);
                case CUSTOMER_ENROLMENT -> new EventCustomerEnrolment(this.messageType,this.body);
                case CUSTOMER_ENROLMENT_CANCELLATION -> new EventCustomerUnrolment(this.messageType,this.body);
                case RESOURCE_RESERVATION -> new EventResourceReservation(this.messageType,this.body);
                case CUSTOMER_UPDATE -> new EventCustomerUpdate(this.messageType,this.body);
                case RESOURCE_RELEASE -> new EventResourceRelease(this.messageType,this.body);
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
