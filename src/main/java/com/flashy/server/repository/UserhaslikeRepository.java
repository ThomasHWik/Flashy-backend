package com.flashy.server.repository;

import com.flashy.server.data.Userhaslike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserhaslikeRepository extends JpaRepository<Userhaslike, Integer> {
    @Modifying
    @Query("DELETE FROM Userhaslike x WHERE x.carddeckid = :carddeckid AND x.flashyuserid = :userid")
    void deleteByUserAndCarddeckid(int userid, int carddeckid);

    @Modifying
    @Query("DELETE FROM Userhaslike x WHERE x.carddeckid = :carddeckid")
    void deleteByCarddeckid(int carddeckid);

    Userhaslike getFirstByFlashyuseridAndCarddeckid(int id, int id1);
}
