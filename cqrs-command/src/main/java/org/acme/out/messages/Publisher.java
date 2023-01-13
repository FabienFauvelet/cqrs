package org.acme.out.messages;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.vertx.core.json.JsonObject;
import org.acme.domain.model.*;
import org.acme.out.messages.model.*;
import org.acme.out.messages.model.customer.CustomerCreationCommonMessage;
import org.acme.out.messages.model.customer.CustomerCreationCommonMessage.CustomerCreationMessageBody.CustomerCreationMessageBodyBuilder;
import org.acme.out.messages.model.customer.CustomerUpdateCommonMessage;
import org.acme.out.messages.model.customer.CustomerUpdateCommonMessage.CustomerUpdateMessageBody.CustomerUpdateMessageBodyBuilder;
import org.acme.out.messages.model.enrolment.CustomerEnrolmentCancellationCommonMessage;
import org.acme.out.messages.model.enrolment.CustomerEnrolmentCommonMessage;
import org.acme.out.messages.model.event.EventCreationCommonMessage;
import org.acme.out.messages.model.event.EventDeletionCommonMessage;
import org.acme.out.messages.model.resource.*;
import org.acme.out.messages.model.shared.Address;
import org.acme.out.messages.model.teacher.TeacherAssignationCommonMessage;
import org.acme.out.messages.model.teacher.TeacherCreationCommonMessage;
import org.acme.out.messages.model.teacher.TeacherUpdateCommonMessage;
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
        publish(new EventCreationCommonMessage(
                EventCreationCommonMessage.getBodyBuilder()
                    .id(event.getId())
                    .startDateTime(event.getStartDateTime())
                    .endDateTime(event.getEndDateTime())
                    .nbMaxParticipant(event.getNbMaxParticipant())
                    .type(event.getType())
                    .build())
        );
    }
    public void publishTeacherAssignation(UUID eventId, Teacher teacher) {
        publish(new TeacherAssignationCommonMessage(
                TeacherAssignationCommonMessage.getBodyBuilder()
                        .teacherId(teacher.getId())
                        .eventId(eventId).build())
        );
    }

    public void publishResourceReservation(UUID eventId, Resource resource) {
        publish(new ResourceReservationCommonMessage(
                ResourceReservationCommonMessage.getBodyBuilder()
                    .resourceId(resource.getId())
                    .eventId(eventId)
                    .build())
        );
    }
    public void publishResourceRelease(UUID eventId, Resource resource) {
        publish(new ResourceReleaseCommonMessage(
                ResourceReleaseCommonMessage.getBodyBuilder()
                        .resourceId(resource.getId())
                        .eventId(eventId)
                        .build())
        );
    }

    public void publishEventDeletion(UUID eventId) {
        publish(new EventDeletionCommonMessage(EventDeletionCommonMessage.getBodyBuilder().id(eventId).build()));
    }

    public void publishNewEnrolment(UUID eventId, UUID customerId) {
        publish(new CustomerEnrolmentCommonMessage(
                CustomerEnrolmentCommonMessage.getBodyBuilder()
                        .eventId(eventId)
                        .customerId(customerId)
                        .build())
        );
    }

    public void publishEnrolmentCancellation(UUID eventId, UUID customerId) {
        publish(new CustomerEnrolmentCancellationCommonMessage(
                CustomerEnrolmentCancellationCommonMessage.getBodyBuilder()
                        .eventId(eventId)
                        .customerId(customerId)
                        .build())
        );
    }


    public void publishCustomerCreation(Customer customer) {
        CustomerCreationMessageBodyBuilder builder = CustomerCreationCommonMessage.getBodyBuilder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate());
        Optional.ofNullable(customer.getAddress()).ifPresent(
                address -> builder.address(
                        CustomerCreationCommonMessage.AddressMessage.builder()
                                .id(address.getId())
                                .street(address.getStreet())
                                .zipCode(address.getZipCode())
                                .city(address.getCity()).build())
        );
        publish(new CustomerCreationCommonMessage(builder.build()));
    }

    public void publishCustomerUpdate(Customer customer) {
        CustomerUpdateMessageBodyBuilder builder = CustomerUpdateCommonMessage.getBodyBuilder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate());
        Optional.ofNullable(customer.getAddress()).ifPresent(address -> builder.address(Address.builder().id(address.getId()).build()));
        publish(new CustomerUpdateCommonMessage(builder.build()));
    }

    public void publishTeacherCreation(Teacher teacher) {
        publish(new TeacherCreationCommonMessage(TeacherCreationCommonMessage.getBodyBuilder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build())
        );
    }

    public void publishTeacherUpdate(Teacher teacher) {
        publish(new TeacherUpdateCommonMessage(TeacherUpdateCommonMessage.getBodyBuilder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .build())
        );
    }

    public void publishTeacherDeletion(UUID teacherId) {
        publish(new TeacherUpdateCommonMessage(TeacherUpdateCommonMessage.getBodyBuilder()
                .id(teacherId)
                .build())
        );
    }

    public void publishResourceCreation(Resource resource) {
        publish(new ResourceCreationCommonMessage(ResourceCreationCommonMessage.getBodyBuilder()
                .id(resource.getId())
                .name(resource.getName())
                .build())
        );
    }

    public void publishResourceUpdate(Resource resource) {
        publish(new ResourceUpdateCommonMessage(ResourceUpdateCommonMessage.getBodyBuilder()
                .id(resource.getId())
                .name(resource.getName())
                .build())
        );
    }

    public void publishResourceDeletion(UUID resourceId) {
        publish(new ResourceDeletionCommonMessage(ResourceDeletionCommonMessage.getBodyBuilder()
                .id(resourceId)
                .build())
        );
    }
}
