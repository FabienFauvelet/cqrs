package org.acme.out.messages;

import io.vertx.core.json.JsonObject;
import org.acme.domain.model.Resource;
import org.acme.domain.model.Teacher;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class Publisher {

    @Channel("main-events")
    Emitter<EventMessage> eventEmitter;

    public void publish(Object object){
        publish(EventMessage.builder().object(object).build());
    }
    
    private void publish(EventMessage message){
        eventEmitter.send(message);
    }

    @Incoming("main")
    public void process(JsonObject message) {
        System.out.println(message);
    }
    public void publish(UUID eventId, Teacher teacher) {
    }

    public void publish(UUID eventId, Resource resource) {
    }
}
