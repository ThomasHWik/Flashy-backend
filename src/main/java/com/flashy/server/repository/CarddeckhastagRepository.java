package com.flashy.server.repository;

import com.flashy.server.data.Carddeckhastag;
import com.flashy.server.data.Tag;
import com.flashy.server.data.Userhaslike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Component
public interface CarddeckhastagRepository extends JpaRepository<Carddeckhastag, Integer> {

    @Query("SELECT x.carddeckid  FROM Carddeckhastag x WHERE x.tagid IN :tagIds GROUP BY x.carddeckid HAVING COUNT(DISTINCT x.tagid) = :tagCount")
    List<Integer> findCarddeckIdsByTags(List<Integer> tagIds, long tagCount);

    @Transactional
    @Modifying
    @Query("DELETE FROM Carddeckhastag x WHERE x.carddeckid = :carddeckid")
    void deleteByCarddeckid(int carddeckid);
}

