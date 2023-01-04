package facade;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import models.Customer;
import models.Resource;
import models.Teacher;
import org.bson.BsonArray;
import org.bson.BsonString;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class InsertionResource
{
    @Inject MongoClient mongoClient;

    //Collection getters
    public MongoCollection<Document> getCoursesCollection(){return mongoClient.getDatabase("courses").getCollection("courses");}
    public MongoCollection<Document> getCustomerCollection(){return mongoClient.getDatabase("references").getCollection("customers");}
    public MongoCollection<Document> getResourcesCollection(){return mongoClient.getDatabase("references").getCollection("resources");}
    public MongoCollection<Document> getTeacherCollection(){return mongoClient.getDatabase("references").getCollection("teachers");}

    public void createEvent(UUID id, LocalDateTime startDateTime, LocalDateTime endDateTime, int nbMaxParticipant)
    {
        InsertOneResult res = getCoursesCollection().insertOne(new Document()
                .append("_id",id.toString())
                .append("startDateTime",startDateTime)
                .append("endDateTime",endDateTime)
                .append("nbMaxParticipant",nbMaxParticipant));
        System.out.println("Insertion : " + res);
    }

    public void enrollCustomer(UUID eventId, UUID customerId)
    {
        //TODO Enrolemnt !
        getCoursesCollection().findOneAndUpdate(new Document().append("_id",eventId.toString()),new Document().append("customers",customerId));
    }

    public void deleteEvent(UUID courseId)
    {
        try
        {
            Document courseDoc = getCoursesCollection().find(new Document().append("_id",courseId.toString())).cursor().next();
            for (Customer c : courseDoc.getList("customers",Customer.class))
            {
                //GetCollection of the actual customer and delete course in his view
                getCoursesCollectionByCustomerId(c.getId().toString()).deleteOne(new Document().append("_id",courseId.toString()));
            }
            //Course deletion
            getCoursesCollection().deleteOne(new Document().append("_id", courseId.toString()));
        }
        catch(NoSuchElementException e)
        {
            System.out.println("L'élément à supprimer n'a pas été trouvé");
        }
    }

    public void updateEvent(UUID eventId)
    {
        getCoursesCollection().updateOne(new Document("_id",eventId.toString()),new Document());
    }

    public void createTeacher(UUID id, String lastname, String firstname)
    {
        getTeacherCollection().insertOne(new Document()
                .append("_id", id.toString())
                .append("lastname",lastname)
                .append("firstname",firstname)
        );
    }

    public void assignTeacher(UUID coursesId, UUID teacherId)
    {

        Document teachDoc = getTeacherCollection().find(new Document().append("_id",teacherId.toString())).cursor().next();
        Document courseDoc = getCoursesCollection().find(new Document().append("_id",coursesId.toString())).cursor().next();

        getCoursesCollection().findOneAndUpdate(new Document().append("_id",coursesId.toString()),
                new Document("teacherId", teacherId.toString())
                        .append("teacherFirstName",teachDoc.getString("firstName"))
                        .append("teacherLastName",teachDoc.getString("lastName")));

        //TODO Itérer sous tous les clients du cours pour insérer le nom & prénom du prof
        for (Customer customer : courseDoc.getList("customers", Customer.class))
        {
            getCoursesCollectionByCustomerId(customer.getId().toString()).findOneAndUpdate(new Document().append("_id",coursesId.toString())
                    ,new Document("teacherId", teacherId.toString())
                    .append("teacherFirstName",teachDoc.getString("firstName"))
                    .append("teacherLastName",teachDoc.getString("lastName")));
        }

    }


    public MongoCollection<Document> getCoursesCollectionByCustomerId(String customerId)
    {
        return mongoClient.getDatabase("courses").getCollection(customerId);
    }

    public void createCustomerCollection(String customerId)
    {
        mongoClient.getDatabase("courses").createCollection(customerId);
    }

    public void addCustomer(String customerId)
    {
        getCustomerCollection().insertOne(new Document().append("_id",customerId));
    }


    public void addResourceToEvent(UUID eventId, UUID resourceId)
    {
        //TODO Rechercher info resources dans refResources puis ajouter les info dans l'event
        MongoCursor<Document> cursor = (MongoCursor<Document>) getResourcesCollection().find(new Document().append("_id", new BsonString(resourceId.toString())));
        Document doc;

        try{
            doc = cursor.next();
        }catch(NoSuchElementException e)
        {
            System.out.println("La ressource n'existe pas dans le référentiel");
            return;
        }

        Resource resource = new Resource(UUID.fromString(doc.getString("_id")), doc.getString("name"));
        //On ajoute un élément dans le calendrier de la resource
        //TODO AddElement
        //On ajoute la resource dans le calendrier du prof
            //Recherche dans l'élément dans le calendrier du prof

    }
}