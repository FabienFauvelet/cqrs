package org.acme.services;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.acme.models.CustomerAgendaElement;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerSlotsService
{
    @Inject
    MongoClient mongoClient;

    public List<CustomerAgendaElement> getAgenda(String id, String start, String end)
    {
        ArrayList<CustomerAgendaElement> res = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        MongoCursor<Document> cursor = (MongoCursor<Document>) getCollection(id).find(new Document().append("$gte",start).append("$lt",end));
        while(cursor.hasNext())
        {
            Document myElement = cursor.next();
            res.add(
                    new CustomerAgendaElement(myElement.getString("name"),
                            myElement.getString("startDate"),
                            myElement.getString("endDate"))
            );
        }

        return res;
    }

    public MongoCollection<Document> getCollection(String id)
    {
        return mongoClient.getDatabase("courses").getCollection(id);
    }


}
