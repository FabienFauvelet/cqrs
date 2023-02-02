package org.acme.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.acme.models.CourseElement;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CourseSlotService
{
    @Inject
    MongoClient mongoClient;

    public List<CourseElement> getAllCourses()
    {
        ArrayList<CourseElement> res = new ArrayList<>();

        MongoCursor<Document> cursor = getCoursesCollection().find().cursor();

        while(cursor.hasNext())
        {
            Document course = cursor.next();
            res.add(
                    new CourseElement(course.getString("_id"),
                            course.getString("type"),
                            course.getInteger("nbMaxParticipant"),
                            course.getDate("startDateTime"),
                            course.getDate("endDateTime"),
                            course.getList("resources",String.class),
                            course.getList("customers",String.class),
                            course.getString("teacherFirstname"),
                            course.getString("teacherLastname"),
                            course.getString("teacherId")
                    ));
        }

        return res;
    }

    public MongoCollection<Document> getCoursesCollection()
    {
        return mongoClient.getDatabase("courses").getCollection("courses");
    }


}
