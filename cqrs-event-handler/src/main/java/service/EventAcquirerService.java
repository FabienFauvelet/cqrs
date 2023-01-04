package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import facade.InsertionResource;
import models.in.EventSwitcher;
import models.in.TopicMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class EventAcquirerService{

    @Inject
    InsertionResource insertionResource;

    @Incoming("main-in")
    public void process(byte[] message)
    {
        //System.out.println("Message reçu : " + new String(message, StandardCharsets.UTF_8));
        String jsonTxtMsg = new String(message,StandardCharsets.UTF_8);

        try {
            EventSwitcher switcher = new ObjectMapper().readValue(jsonTxtMsg, EventSwitcher.class);
            TopicMessage genericMsg = switcher.toInsertObjectType();

            genericMsg.insertObject(insertionResource);


            System.out.println("Réussite  ! : " + genericMsg.getMessageType().toString());

        } catch (JsonProcessingException e) {
            System.out.print("GRAVE ERREUR : \n");
            e.printStackTrace();
        }
    }
}
