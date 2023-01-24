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
        delete("id",courseId);
    }
}