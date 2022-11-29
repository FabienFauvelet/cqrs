package org.acme.out.postgres.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.out.postgres.entity.TeacherEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class TeacherRepository implements PanacheRepository<TeacherEntity> {
    public boolean exists(UUID id) {
        return count("id",id) > 0;
    }
}
