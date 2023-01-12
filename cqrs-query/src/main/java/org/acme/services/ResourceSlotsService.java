package org.acme.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.acme.models.ResourceAgendaElement;
import org.acme.models.TeacherAgendaElement;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ResourceSlotsService
{
    @Inject MongoClient mongoClient;

    public List<ResourceAgendaElement> getAgenda(String id, String start, String end)
    {
        List<ResourceAgendaElement> res = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        Document query = new Document().append("startDateTime",new Document().append("$gte",startDate))
                .append("endDateTime",new Document().append("$lt",endDate));

        MongoCursor<Document> cursor = getCollection(id).find(query).cursor();

        while(cursor.hasNext())
        {
            Document elem = cursor.next();
            res.add(
                    new ResourceAgendaElement(
                            elem.getString("_id"),
                            elem.getString("courseType"),
                            elem.getDate("startDateTime"),
                            elem.getDate("endDateTime"),
                            elem.getString("teacherFullname")
            ));
        }
        return res;
    }

    private MongoCollection<Document> getCollection(String id)
    {
        return mongoClient.getDatabase("resources").getCollection(id);
    }
}
