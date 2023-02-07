package models.referentiel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Course;
import models.referentiel.entities.CourseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class CourseRepository implements PanacheRepository<CourseEntity>
{
    @Transactional
    public void createCourse(Course course)
    {
        persistAndFlush(course.toEntity());
    }

    @Transactional
    public void deleteCourse(UUID courseId)
    {
        CourseEntity ent = find("id",courseId).firstResult();
        System.out.println("Suppression : Id = " + ent.getId().toString());
        System.out.println("Alive ? : " + ent.isAlive());
        ent.setAlive(false);
        System.out.println("Alive now  ? : " + ent.isAlive());
        persistAndFlush(ent);
    }

    @Transactional
    public void updateCourse(Course course)
    {
        CourseEntity entCourse = find("id",course.getId()).firstResult();
        entCourse.setAlive(course.isAlive());
        entCourse.setEndDateTime(course.getEndDateTime());
        entCourse.setStartDateTime(course.getStartDateTime());
        entCourse.setType(course.getType());
        entCourse.setNbMaxParticipant(course.getNbMaxParticipant());
        persist(entCourse);
    }
}