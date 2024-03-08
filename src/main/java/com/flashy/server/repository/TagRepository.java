package com.flashy.server.repository;

import com.flashy.server.data.Tag;
import com.flashy.server.data.Userhaslike;

import org.springframework.data.domain.Pageable;
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
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findFirstByName(String name);

    default Tag insertIfNotExists(String name) {
        Tag t = findFirstByName(name);
        return t == null ? save(new Tag(name)) : t;
    }

    @Query("SELECT t.name FROM Tag t WHERE t.name LIKE :searchquery% ORDER BY t.name ASC")
    List<String> searchTags(Pageable pageable, String searchquery);
}

