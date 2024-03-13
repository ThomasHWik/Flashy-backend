package com.flashy.server.repository;

import com.flashy.server.data.Comment;
import com.flashy.server.data.Userhasfavorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT x FROM Comment x WHERE x.carddeckid = :id")
    List<Comment> findAllByCarddeckid(int id);

    @Modifying
    @Query("DELETE FROM Comment x WHERE x.carddeckid = :carddeckid")
    void deleteByCarddeckid(int carddeckid);

    Comment getFirstByUuid(String uuid);

    @Modifying
    @Query("DELETE FROM Comment x WHERE x.flashyuserid = :id")
    void deleteByFlashyuserid(int id);
}
