package service;

import facade.AgendaResource;
import io.vertx.core.json.JsonObject;
import models.in.EventSwitcher;
import models.in.TopicMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventAcquirerService{

    @Inject
    AgendaResource agendaResource;

    @Incoming("main-in-channel")
    public void process(JsonObject message)
    {
            System.out.println(message.toString());
            EventSwitcher switcher = message.mapTo(EventSwitcher.class);
            TopicMessage genericMsg = switcher.toInsertObjectType();
            genericMsg.insertObject(agendaResource);
    }
}
