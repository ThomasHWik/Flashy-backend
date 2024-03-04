package com.flashy.server.repository;

import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface CarddeckRepository extends JpaRepository<Carddeck, Long> {
        Carddeck getFirstByUuid(String uuid);

        @Query("SELECT x FROM Carddeck x WHERE x.flashyuserid = :id AND x.isprivate = 0")
        List<Carddeck> getAllByFlashyuseridEqualsAndNotAuthorized(int id);

        @Query("SELECT x FROM Carddeck x WHERE x.flashyuserid = :id")
        List<Carddeck> getAllByFlashyuseridEqualsAndAuthorized(int id);

        @Modifying
        void deleteById(int id);

        @Modifying
        @Query("DELETE FROM Carddeck x WHERE x.flashyuserid = :id")
        void deleteByFlashyuserid(int id);


        @Query("SELECT x.id, x.uuid, x.title, x.isprivate, x.flashyuserid, (SELECT COUNT(y) FROM Flashcard y WHERE y.carddeckid = x.id) as cardcount, (SELECT COUNT(z) FROM Userhaslike z WHERE z.carddeckid = x.id) as likecount, (SELECT COUNT(a) FROM Userhasfavorite a WHERE a.carddeckid = x.id) as favoritecount FROM Carddeck x WHERE x.isprivate = 0")
        Page<Object[]> findExtendedCarddecks(Pageable pageable);

}