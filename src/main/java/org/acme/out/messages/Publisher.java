package org.acme.out.messages;

import org.acme.domain.model.Customer;
import org.acme.domain.model.Event;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.acme.out.messages.model.*;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class Publisher {

    @Channel("main-out")
    Emitter<Message> eventEmitter;

    private void publish(Message message){
        eventEmitter.send(message);
    }

    @Incoming("main-in")
    public void process(byte[] message) {
        System.out.println(new String(message));
    }

    public void publish(Event event){
        publish(new EventCreationMessage(
                EventCreationMessage.getBodyBuilder()
                    .id(event.getId())
                    .startDateTime(event.getStartDateTime())
                    .endDateTime(event.getEndDateTime())
                    .nbMaxParticipant(event.getNbMaxParticipant())
                    .build())
        );
    }
    public void publishTeacherAssignation(UUID eventId, Teacher teacher) {
        publish(new TeacherAssignationMessage(
                TeacherAssignationMessage.getBodyBuilder()
                        .teacherId(teacher.getId())
                        .eventId(eventId).build())
        );
    }

    public void publishResourceReservation(UUID eventId, Resource resource) {
        publish(new ResourceReservationMessage(
                ResourceReservationMessage.getBodyBuilder()
                    .resourceId(resource.getId())
                    .eventId(eventId)
                    .build())
        );
    }
    public void publishResourceRelease(UUID eventId, Resource resource) {
        publish(new ResourceReleaseMessage(
                ResourceReleaseMessage.getBodyBuilder()
                        .resourceId(resource.getId())
                        .eventId(eventId)
                        .build())
        );
    }

    public void publishEventDeletion(UUID eventId) {
        publish(new EventDeletionMessage(EventDeletionMessage.getBodyBuilder().id(eventId).build()));
    }

    public void publishNewEnrolment(UUID eventId, UUID customerId) {
        publish(new CustomerEnrolmentMessage(
                CustomerEnrolmentMessage.getBodyBuilder()
                        .eventId(eventId)
                        .customerId(customerId)
                        .build())
        );
    }

    public void publishEnrolmentCancellation(UUID eventId, UUID customerId) {
        publish(new CustomerEnrolmentCancellationMessage(
                CustomerEnrolmentCancellationMessage.getBodyBuilder()
                        .eventId(eventId)
                        .customerId(customerId)
                        .build())
        );
    }


    public void publishCustomerCreation(Customer customer) {
        publish(new CustomerCreationMessage(CustomerCreationMessage.getBodyBuilder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .addressId(customer.getAddress().getId())
                .build()));
    }
}
