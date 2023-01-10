package org.acme.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.acme.models.TeacherAgendaElement;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TeacherSlotsService
{
    @Inject
    MongoClient mongoClient;

    public List<TeacherAgendaElement> getAgenda(String id, String start, String end)
    {
        List<TeacherAgendaElement> res = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        Document query = new Document().append("startDateTime",new Document().append("$gte",startDate))
                .append("endDateTime",new Document().append("$lt",endDate));

        MongoCursor<Document> cursor = getCollection(id).find(query).cursor();

        while(cursor.hasNext())
        {
            Document elem = cursor.next();
            //res.add(
                    //new TeacherAgendaElement(elem.getString("_id"),elem.getString("type"))
            //);

        }
        return res;
    }

    private MongoCollection<Document> getCollection(String id)
    {
        return mongoClient.getDatabase("teachers").getCollection(id);
    }


}
