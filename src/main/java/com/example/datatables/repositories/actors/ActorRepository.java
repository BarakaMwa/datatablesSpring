package com.example.datatables.repositories.actors;

import com.example.datatables.models.actors.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findAll();
}
