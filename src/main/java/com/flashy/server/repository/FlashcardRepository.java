package com.flashy.server.repository;

import com.flashy.server.data.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    Flashcard findById(int id);


}