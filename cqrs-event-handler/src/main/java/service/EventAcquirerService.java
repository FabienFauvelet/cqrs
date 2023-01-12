package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import facade.AgendaResource;
import models.in.EventSwitcher;
import models.in.TopicMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class EventAcquirerService{

    @Inject
    AgendaResource agendaResource;

    @Incoming("main-in")
    public void process(byte[] message)
    {
        //System.out.println("Message reçu : " + new String(message, StandardCharsets.UTF_8));
        String jsonTxtMsg = new String(message,StandardCharsets.UTF_8);
        try
        {
            EventSwitcher switcher = new ObjectMapper().findAndRegisterModules().readValue(jsonTxtMsg,EventSwitcher.class);
            TopicMessage genericMsg = switcher.toInsertObjectType();

            genericMsg.insertObject(agendaResource);

            //System.out.println("Réussite : " + genericMsg.getMessageType().toString());

        }
        catch (JsonProcessingException e)
        {
            System.out.print("Erreur JSON : \n");
            e.printStackTrace();
        }
    }
}
