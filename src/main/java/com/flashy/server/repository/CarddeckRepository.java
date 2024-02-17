package com.flashy.server.repository;

import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface CarddeckRepository extends JpaRepository<Carddeck, Long> {
        Carddeck getFirstByUuid(String uuid);

        @Query("SELECT x FROM Carddeck x WHERE x.flashyuser_id = :id AND x.isprivate = 0")
        List<Carddeck> getAllByFlashyuser_idEqualsAndNotAuthorized(int id);

        @Query("SELECT x FROM Carddeck x WHERE x.flashyuser_id = :id")
        List<Carddeck> getAllByFlashyuser_idEqualsAndAuthorized(int id);




}