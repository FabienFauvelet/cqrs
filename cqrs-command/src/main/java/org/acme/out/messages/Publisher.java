package org.acme.out.messages;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.vertx.core.json.JsonObject;
import org.acme.domain.model.*;
import org.acme.out.messages.model.*;
import org.acme.out.messages.model.customer.CustomerCreationMessage;
import org.acme.out.messages.model.customer.CustomerCreationMessage.CustomerCreationMessageBody.CustomerCreationMessageBodyBuilder;
import org.acme.out.messages.model.customer.CustomerUpdateMessage;
import org.acme.out.messages.model.customer.CustomerUpdateMessage.CustomerUpdateMessageBody.CustomerUpdateMessageBodyBuilder;
import org.acme.out.messages.model.enrolment.CustomerEnrolmentCancellationMessage;
import org.acme.out.messages.model.enrolment.CustomerEnrolmentMessage;
import org.acme.out.messages.model.event.EventCreationMessage;
import org.acme.out.messages.model.event.EventDeletionMessage;
import org.acme.out.messages.model.event.EventUpdateMessage;
import org.acme.out.messages.model.resource.*;
import org.acme.out.messages.model.shared.Address;
import org.acme.out.messages.model.teacher.TeacherAssignationMessage;
import org.acme.out.messages.model.teacher.TeacherCreationMessage;
import org.acme.out.messages.model.teacher.TeacherUnassignationMessage;
import org.acme.out.messages.model.teacher.TeacherUpdateMessage;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class Publisher {

    @Channel("main-out-channel")
    @Broadcast
    Emitter<CommonMessage> eventEmitter;
    private void publish(CommonMessage message){
        eventEmitter.send(message);
    }

    @Incoming("main-in-channel")
    public void process(JsonObject message) {
        System.out.println(message.toString());
    }

    public void publish(Event event){
        publish(new EventCreationMessage(
                EventCreationMessage.getBodyBuilder()
                    .id(event.getId())
                    .startDateTime(event.getStartDateTime())
                    .endDateTime(event.getEndDateTime())
                    .nbMaxParticipant(event.getNbMaxParticipant())
                    .type(event.getType())
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
    public void publishTeacherUnassignation(UUID eventId, Teacher teacher) {
        publish(new TeacherUnassignationMessage(
                TeacherUnassignationMessage.getBodyBuilder()
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
        CustomerCreationMessageBodyBuilder builder = CustomerCreationMessage.getBodyBuilder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate());
        Optional.ofNullable(customer.getAddress()).ifPresent(
                address -> builder.address(
                        CustomerCreationMessage.AddressMessage.builder()
                                .id(address.getId())
                                .street(address.getStreet())
                                .zipCode(address.getZipCode())
                                .city(address.getCity()).build())
        );
        publish(new CustomerCreationMessage(builder.build()));
    }

    public void publishCustomerUpdate(Customer customer) {
        CustomerUpdateMessageBodyBuilder builder = CustomerUpdateMessage.getBodyBuilder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate());
        Optional.ofNullable(customer.getAddress()).ifPresent(address -> builder.address(Address.builder().id(address.getId()).build()));
        publish(new CustomerUpdateMessage(builder.build()));
    }

    public void publishTeacherCreation(Teacher teacher) {
        publish(new TeacherCreationMessage(TeacherCreationMessage.getBodyBuilder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build())
        );
    }

    public void publishTeacherUpdate(Teacher teacher) {
        publish(new TeacherUpdateMessage(TeacherUpdateMessage.getBodyBuilder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build())
        );
    }

    public void publishTeacherDeletion(UUID teacherId) {
        publish(new TeacherUpdateMessage(TeacherUpdateMessage.getBodyBuilder()
                .id(teacherId)
                .build())
        );
    }

    public void publishResourceCreation(Resource resource) {
        publish(new ResourceCreationMessage(ResourceCreationMessage.getBodyBuilder()
                .id(resource.getId())
                .name(resource.getName())
                .build())
        );
    }

    public void publishResourceUpdate(Resource resource) {
        publish(new ResourceUpdateMessage(ResourceUpdateMessage.getBodyBuilder()
                .id(resource.getId())
                .name(resource.getName())
                .build())
        );
    }

    public void publishResourceDeletion(UUID resourceId) {
        publish(new ResourceDeletionMessage(ResourceDeletionMessage.getBodyBuilder()
                .id(resourceId)
                .build())
        );
    }

    public void publishEventUpdate(Event event) {
        publish(new EventUpdateMessage(
                EventUpdateMessage.getBodyBuilder()
                        .id(event.getId())
                        .startDateTime(event.getStartDateTime())
                        .endDateTime(event.getEndDateTime())
                        .nbMaxParticipant(event.getNbMaxParticipant())
                        .type(event.getType())
                        .build())
        );
    }
}
