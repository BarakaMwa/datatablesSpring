package com.example.datatables.repositories.actors;

import com.example.datatables.dtos.ActorIdFirstLastDto;
import com.example.datatables.models.actors.Actor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findAll();

    //using top jpa
    List<Actor> findTop100ByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String searchValue, String value);

    List<Actor> findTop10ByActorIdIsNotNull();

    //using pageable
    List<Actor> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String searchValue, String value, Pageable pageable);

    //using Dto
    @Query(value = "select new com.example.datatables.dtos.ActorIdFirstLastDto(a.actorId,a.firstName,a.lastName) from Actor a where a.lastName like %:lastName% or a.firstName like %:firstName%")
    List<ActorIdFirstLastDto> getByFirstOrLastAndId(@Param("lastName") String lastname, @Param("firstName") String firstName, Pageable pageable);

    List<Actor> findTop100ByActorIdIsNotNull();
}
