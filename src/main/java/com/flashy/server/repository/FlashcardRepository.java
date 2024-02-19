package com.flashy.server.repository;

import com.flashy.server.data.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    Flashcard findById(int id);

    @Query("SELECT x FROM Flashcard x WHERE x.carddeck_id = :id")
    List<Flashcard> findAllByCarddeckId(int id);

    @Modifying
    @Query("DELETE FROM Flashcard x WHERE x.carddeck_id = :id")
    void deleteByCarddeckId(int id);

}