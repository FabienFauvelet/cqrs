package models.referentiel.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Teacher;
import models.referentiel.entities.TeacherEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class TeacherRepository implements PanacheRepository<TeacherEntity>
{
    @Transactional
    public void createTeacher(TeacherEntity teacher)
    {
        persistAndFlush(teacher);
    }

    @Transactional
    public void deleteTeacher(UUID teacherId)
    {
        delete("id", teacherId);
    }
}
