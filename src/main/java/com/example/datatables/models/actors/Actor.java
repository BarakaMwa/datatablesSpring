package com.example.datatables.models.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "actor", schema = "sakila")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", nullable = false)
    private long actorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "last_update", nullable = false)
    private java.sql.Timestamp lastUpdate;

}
