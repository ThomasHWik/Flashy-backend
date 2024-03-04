package com.flashy.server.repository;

import com.flashy.server.data.Userhasfavorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserhasfavoriteRepository extends JpaRepository<Userhasfavorite, Integer> {
    @Modifying
    @Query("DELETE FROM Userhasfavorite x WHERE x.carddeckid = :carddeckid AND x.flashyuserid = :userid")
    void deleteByUserAndCarddeckid(int userid, int carddeckid);
}