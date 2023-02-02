package facade;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.InsertOneResult;
import models.Course;
import models.Customer;
import models.Resource;
import models.Teacher;
import models.in.CustomerAddress;
import models.referentiel.entities.CustomerEntity;
import models.referentiel.entities.ResourceEntity;
import models.referentiel.entities.TeacherEntity;
import models.referentiel.repositories.CourseRepository;
import models.referentiel.repositories.CustomerRepository;
import models.referentiel.repositories.ResourceRepository;
import models.referentiel.repositories.TeacherRepository;
import org.bson.BsonString;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class AgendaResource
{
    @Inject MongoClient mongoClient;
    @Inject CustomerRepository customerRepository;
    @Inject ResourceRepository resourceRepository;
    @Inject TeacherRepository teacherRepository;
    @Inject CourseRepository courseRepository;

    //Collection getters
    public MongoCollection<Document> getCoursesCollection(){return mongoClient.getDatabase("courses").getCollection("courses");}
    public MongoCollection<Document> getCustomerCollection(){return mongoClient.getDatabase("references").getCollection("customers");}
    public MongoCollection<Document> getResourcesCollection(){return mongoClient.getDatabase("references").getCollection("resources");}
    public MongoCollection<Document> getTeacherCollection(){return mongoClient.getDatabase("references").getCollection("teachers");}
    public MongoCollection<Document> getTeacherCollectionById(String teacherId){return mongoClient.getDatabase("teachers").getCollection(teacherId);}
    public MongoCollection<Document> getCoursesCollectionByCustomerId(String customerId){return mongoClient.getDatabase("courses").getCollection(customerId);}
    public MongoCollection<Document> getResourceCollectionById(String resourceId){return mongoClient.getDatabase("resources").getCollection(resourceId);}

    public void createCustomerCollection(String customerId)
    {
        mongoClient.getDatabase("courses").createCollection(customerId);
    }

    public void createResourceCollection(String resourceId)
    {
        mongoClient.getDatabase("resources").createCollection(resourceId);
    }

    public void createTeacherCollection(String teacherId)
    {
        mongoClient.getDatabase("teachers").createCollection(teacherId);
    }

    //Creation d'un cours
    public void createEvent(UUID id, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int nbMaxParticipant)
    {
        InsertOneResult res = getCoursesCollection().insertOne(new Document()
                .append("_id",id.toString())
                .append("type",type)
                .append("startDateTime",startDateTime)
                .append("endDateTime",endDateTime)
                .append("nbMaxParticipant",nbMaxParticipant)
                .append("customers",new ArrayList<String>())
                .append("resources",new ArrayList<String>()));

        //System.out.println("Acknowledged : " + res.wasAcknowledged());
        courseRepository.createCourse(
                new Course(id,type,startDateTime,endDateTime,nbMaxParticipant));
    }

    //Creation d'un client
    public void createCustomer(UUID customerId, String firstname, String lastname, Date birthdate, CustomerAddress address)
    {
        //Creation de la collection dédiée
        createCustomerCollection(customerId.toString());

        //Creation du client dans la collection clients
        getCustomerCollection().insertOne(new Document()
                .append("_id",customerId.toString())
                .append("firstname",firstname)
                .append("lastname",lastname)
                .append("birthdate",birthdate)
                .append("address",address.toString())
        );

        customerRepository.createCustomer(new CustomerEntity(customerId,firstname,lastname,birthdate,address.toString()));
    }

    public void updateCustomer(UUID customerId, String firstname, String lastname, Date birthdate, CustomerAddress address)
    {
        getCustomerCollection().findOneAndUpdate(
                new Document().append("_id", customerId.toString()),
                new Document().append("$set", new Document().append("firstname",firstname)
                        .append("lastname",lastname)
                        .append("birthdate",birthdate)
                        .append("address",address))
        );
    }

    //Inscription d'un membre à un cours
    public void enrollCustomer(UUID eventId, UUID customerId)
    {
        //Ajout du client sur le cours
        Document d = getCoursesCollection().findOneAndUpdate(new Document().append("_id",eventId.toString())
                ,new Document().append("$push", new Document().append("customers",customerId.toString()))
                ,new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

        //Ajout de l'élément d'agenda dans le calendrier du client
        getCoursesCollectionByCustomerId(customerId.toString()).insertOne(new Document()
                .append("_id",d.getString("_id"))
                .append("type",d.getString("type"))
                .append("startDateTime",d.getDate("startDateTime"))
                .append("endDateTime",d.getDate("endDateTime"))
                .append("teacherFirstname",d.getString("teacherFirstname"))
                .append("teacherLastname",d.getString("teacherLastname"))
        );

        //Ajouts du nom prénom du client dans l'élément de calendrier du prof
        if(!"".equals(d.getString("teacherId")) || !(d.getString("teacherId") == null)) //il y a un prof sur le cours
        {
            Document d_customer = getCustomerCollection().find(new Document().append("_id",customerId.toString())).cursor().next();
            getTeacherCollectionById(d.getString("teacherId")).findOneAndUpdate(
                    new Document().append("_id",eventId.toString())
                    ,new Document().append("$push", new Document().append("customers",d_customer.getString("firstname") + " " + d_customer.getString("lastname")))
            );
        }
    }

    //Désinscription d'un membre à un cours
    public void unrollCustomer(UUID eventId, UUID customerId)
    {
        //Suppression dans la liste des participants du cours
        Document d = getCoursesCollection().findOneAndUpdate(new Document().append("_id",eventId.toString())
                ,new Document().append("$pull", new Document().append("customers",customerId.toString()))
                ,new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

        //Suppression de l'élément dans le calendrier du client
        getCoursesCollectionByCustomerId(customerId.toString()).deleteOne(new Document().append("_id",eventId.toString()));

        Document fullCustomerInfo = getCustomerCollection().find(new Document().append("_id",customerId.toString())).first();

        //Supprimer le nom/prénom dans l'agenda du prof
        getTeacherCollectionById(d.getString("teacherId")).findOneAndUpdate(
                new Document().append("_id",eventId.toString()),
                        new Document().append("$pull",
                                        new Document().append("customers",
                                                fullCustomerInfo.getString("firstname") + " " + fullCustomerInfo.getString("lastname"))));
    }

    public void deleteEvent(UUID courseId)
    {
        try
        {
            Document courseDoc = getCoursesCollection().find(new Document().append("_id",courseId.toString())).cursor().next();
            for (String c : courseDoc.getList("customers",String.class))
            {
                //GetCollection of the actual customer and delete course in his view
                getCoursesCollectionByCustomerId(c).deleteOne(new Document().append("_id",courseId.toString()));
            }

            for (String resource : courseDoc.getList("resources",String.class))
            {
                getResourceCollectionById(resource).deleteOne(
                        new Document().append("_id",courseId.toString())
                );
            }
            //Course deletion
            getCoursesCollection().deleteOne(new Document().append("_id", courseId.toString()));
            getTeacherCollectionById(courseDoc.getString("teacherId")).deleteOne(new Document().append("_id",courseId.toString()));
        }
        catch(NoSuchElementException e)
        {
            System.out.println("L'élément à supprimer n'a pas été trouvé");
        }

        courseRepository.deleteCourse(courseId);
    }

    public void updateEvent(UUID id, String type, int nbMaxParticipant, LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        Document n_course = getCoursesCollection().findOneAndUpdate(
                new Document().append("_id",id.toString()), new Document().append(
                        "$set", new Document()
                                .append("nbMaxParticipant", nbMaxParticipant)
                                .append("startDateTime", startDateTime)
                                .append("endDateTime",endDateTime)
                                .append("type",type)),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

        for(String c : n_course.getList("customers", String.class))
        {
            getCoursesCollectionByCustomerId(c).findOneAndUpdate(
                    new Document().append("_id",id.toString()),
                    new Document().append("$set",
                            new Document().append("startDateTime", startDateTime)
                                    .append("endDateTime",endDateTime)
                                    .append("type",type)));
        }

        for(String r : n_course.getList("resources",String.class))
        {
            getResourceCollectionById(r).findOneAndUpdate(
                    new Document().append("_id",id.toString()),
                    new Document().append("$set",
                            new Document().append("startDateTime", startDateTime)
                                    .append("endDateTime",endDateTime)
                                    .append("courseType",type)));
        }

        courseRepository.updateCourse(new Course(id,type,startDateTime,endDateTime, nbMaxParticipant));
    }

    public void createTeacher(UUID id, String lastname, String firstname)
    {
        getTeacherCollection().insertOne(new Document()
                .append("_id", id.toString())
                .append("lastname",lastname)
                .append("firstname",firstname)
        );
        createTeacherCollection(id.toString());
        teacherRepository.createTeacher(new TeacherEntity(id,firstname,lastname));
    }

    public void assignTeacher(UUID coursesId, UUID teacherId)
    {

        Document teachDoc = getTeacherCollection().find(new Document().append("_id",teacherId.toString())).cursor().next();
        Document courseDoc = getCoursesCollection().findOneAndUpdate(new Document().append("_id",coursesId.toString()),
                new Document("$set", new Document().append("teacherId",teacherId.toString())
                        .append("teacherFirstname",teachDoc.getString("firstname"))
                        .append("teacherLastname",teachDoc.getString("lastname"))));


        getTeacherCollectionById(teacherId.toString()).insertOne(
                new Document()
                        .append("_id",courseDoc.getString("_id"))
                        .append("type",courseDoc.getString("type"))
                        .append("startDateTime",courseDoc.getDate("startDateTime"))
                        .append("endDateTime",courseDoc.getDate("endDateTime"))
                        .append("resources",new ArrayList<String>())
                        .append("customers",new ArrayList<String>())
        );

        for (String customer : courseDoc.getList("customers", String.class))
        {
            getCoursesCollectionByCustomerId(customer).findOneAndUpdate(new Document().append("_id",coursesId.toString())
                    ,new Document()
                    .append("teacherFirstname",teachDoc.getString("firstname"))
                    .append("teacherLastname",teachDoc.getString("lastname")));

            Document d_customer = getCustomerCollection().find(new Document().append("_id",customer)).cursor().next();

            getTeacherCollectionById(teacherId.toString()).findOneAndUpdate(
                    new Document().append("_id", coursesId.toString()),
                    new Document().append("$push",
                            new Document()
                                    .append("customers",d_customer.getString("firstname") +" "+ d_customer.getString("lastname")))

            );
        }

        for(String resource : courseDoc.getList("resources",String.class)) //Ajout de toutes les resource dans le calendrier du prof
        {
            Document d_ref_resource = getResourcesCollection().find(new Document().append("_id",resource)).cursor().next();
            getTeacherCollectionById(teacherId.toString()).findOneAndUpdate(
                    new Document().append("_id", coursesId.toString()),
                    new Document().append("$push",
                            new Document()
                                    .append("resources",d_ref_resource.getString("name")))

            );

            getResourceCollectionById(resource).findOneAndUpdate(
                    new Document().append("_id",coursesId.toString()),
                    new Document().append("$set",
                            new Document()
                                    .append("teacherFullname", teachDoc.getString("firstname") + " " + teachDoc.getString("lastname")))
            );
        }
    }


    public void createResource(UUID resourceId, String name)
    {
        //Creation dans le référentiel
        getResourcesCollection().insertOne(new Document()
                .append("_id",resourceId.toString())
                .append("name", name));

        //Creation du calendrier de la resource
        createResourceCollection(resourceId.toString());
        resourceRepository.createResource(new ResourceEntity(resourceId,name));
    }

    public void addResourceToEvent(UUID eventId, UUID resourceId)
    {
        MongoCursor<Document> cursor = getResourcesCollection().find(new Document().append("_id", new BsonString(resourceId.toString()))).cursor();
        Document doc;

        try{
            doc = cursor.next();
        }catch(NoSuchElementException e)
        {
            System.out.println("La ressource n'existe pas dans le référentiel");
            return;
        }

        Resource resource = new Resource(UUID.fromString(doc.getString("_id")), doc.getString("name"));

        //On ajoute le nom de la resource dans le cours
        Document updatedCourse = getCoursesCollection().findOneAndUpdate(new Document().append("_id",eventId.toString()),
                new Document().append("$push", new Document().append("resources",resource.getId().toString())),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
                );

        //On ajoute un élément dans le calendrier de la resource
        getResourceCollectionById(resourceId.toString()).insertOne(
                new Document().append("_id", updatedCourse.getString("_id"))
                        .append("courseType",updatedCourse.getString("type"))
                        .append("startDateTime", updatedCourse.getDate("startDateTime"))
                        .append("endDateTime",updatedCourse.getDate("endDateTime"))
        );

        if(!"".equals(updatedCourse.getString("teacherId")) && !(updatedCourse.getString("teacherId") == null)) //Prof assigné => Ajout du prof pour la resource et inversement
        {
            //Ajout du prof dans l'agenda de la resource
            getResourceCollectionById(resource.getId().toString()).findOneAndUpdate(
                    new Document().append("_id",eventId.toString()),
                    new Document().append("$set",
                            new Document().append("teacherFirstname",updatedCourse.getString("teacherFirstname"))
                                    .append("teacherLastname",updatedCourse.getString("teacherLastname"))));
            //Ajout de la resource dans le cours de l'agenda du prof

            getTeacherCollectionById(updatedCourse.getString("teacherId")).findOneAndUpdate(
                    new Document().append("_id",eventId.toString()),
                            new Document().append("$push", new Document().append("resources", resource.getName())));
        }
    }

    public void deleteTeacher(UUID teacherId)
    {
        //Suppression du referentiel mongo
        Document teacherDoc = getTeacherCollection().findOneAndDelete(
                new Document().append("_id",teacherId.toString()));

        //Suppression dans les cours concernés (ref cours + agenda client + agenda resource)
        MongoCursor<Document> cursor = getTeacherCollectionById(teacherId.toString()).find().cursor();
        while(cursor.hasNext())
        {
            Document course = cursor.next();
            getCoursesCollection().findOneAndUpdate(new Document().append("_id",course.getString("_id")),
                    new Document().append("$set", new Document().append("teacherFirstname","")
                            .append("teacherLastname","")
                            .append("teacherId","")));
            for(String c : course.getList("customers",String.class))
            {
                getCoursesCollectionByCustomerId(c).findOneAndUpdate(
                        new Document().append("_id",course.getString("_id")),
                        new Document().append("$set", new Document().append("teacherFirstname","")
                                .append("teacherLastname",""))
                );
            }
            for(String r : course.getList("resources",String.class))
            {
                getResourceCollectionById(r).findOneAndUpdate(new Document().append("_id",course.getString("_id")),
                        new Document().append("$set", new Document().append("teacherFullname","")));
            }
        }
        getTeacherCollectionById(teacherId.toString()).drop();
        teacherRepository.deleteTeacher(teacherId);
    }

    public void deleteResource(UUID resourceId)
    {
        //On supprime la resource du référentiel mongo
        Document resDoc = getResourcesCollection().findOneAndDelete(new Document().append("_id",resourceId.toString()));
        //On supprime la reference de la resource dans tous les cours
        MongoCursor<Document> cursor = getResourceCollectionById(resourceId.toString()).find().cursor();
        while(cursor.hasNext())
        {
            Document course = cursor.next();
            Document updatedCourse = getCoursesCollection().findOneAndUpdate(new Document().append("_id",course.getString("_id")),
                    new Document().append("$pull",
                            new Document().append("resources", resourceId.toString())));

            //Si il y a un prof, on enlève le nom de la resource dans son agenda
            if(!"".equals(updatedCourse.getString("teacherId")) && !(updatedCourse.getString("teacherId") == null)) //Prof assigné => Suppression label
            {
                getTeacherCollectionById(updatedCourse.getString("teacherId")).findOneAndUpdate(
                        new Document().append("_id", updatedCourse.getString("_id")),
                                new Document().append("$pull", new Document().append("resources", resDoc.getString("name"))));
            }
        }
        //On supprime la collection de la resource
        getResourceCollectionById(resourceId.toString()).drop();

        resourceRepository.deleteResource(resourceId);
    }
}