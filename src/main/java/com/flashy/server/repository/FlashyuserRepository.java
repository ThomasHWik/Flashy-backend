package com.flashy.server.repository;

import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.data.Flashyuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface FlashyuserRepository extends JpaRepository<Flashyuser, Long> {
        Flashyuser getFirstByUsername(String username);

        Flashyuser getById(int id);

        @Modifying
        @Query("DELETE FROM Flashyuser x WHERE x.username = :username")
        void deleteByUsername(String username);

}