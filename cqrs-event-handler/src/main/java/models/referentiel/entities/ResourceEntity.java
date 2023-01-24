package models.referentiel.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "resource")
@AllArgsConstructor
@NoArgsConstructor
public class ResourceEntity
{
    @Id private UUID id;
    @Column(name = "name")
    private String name;
}
