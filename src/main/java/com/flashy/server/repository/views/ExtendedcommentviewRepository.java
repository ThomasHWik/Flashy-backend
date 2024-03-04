package com.flashy.server.repository.views;


import com.flashy.server.data.dataviews.Extendedcarddeckview;
import com.flashy.server.data.dataviews.Extendedcommentview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ExtendedcommentviewRepository extends JpaRepository<Extendedcommentview, Long> {
    List<Extendedcommentview> findAllByCarddeckuuid(String carddeckuuid);

}