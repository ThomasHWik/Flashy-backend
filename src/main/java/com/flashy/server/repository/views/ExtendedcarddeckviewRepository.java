package com.flashy.server.repository.views;

import com.flashy.server.data.dataviews.Extendedcarddeckview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface ExtendedcarddeckviewRepository extends JpaRepository<Extendedcarddeckview, Long> {

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0")
    Page<Extendedcarddeckview> findAllPublic(Pageable pageable);

    Extendedcarddeckview getFirstByUuid(String uuid);

}