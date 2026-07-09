package net.ent.entc.poeleague.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@MappedSuperclass
@ToString(callSuper=true, of = {"id"})
public abstract class AbstractEntity {
    @Column
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
