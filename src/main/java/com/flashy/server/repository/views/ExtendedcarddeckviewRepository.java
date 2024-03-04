package com.flashy.server.repository.views;

import com.flashy.server.data.dataviews.Extendedcarddeckview;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface ExtendedcarddeckviewRepository extends JpaRepository<Extendedcarddeckview, Long> {

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0")
    Page<Extendedcarddeckview> findAllPublic(Pageable pageable);

    Extendedcarddeckview getFirstByUuid(String uuid);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.likecount DESC")
    Page<Extendedcarddeckview> findAllPublicOrderbyLikecountDesc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.favoritecount DESC")
    Page<Extendedcarddeckview> findAllPublicOrderbyFavoritecountDesc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.cardcount DESC")
    Page<Extendedcarddeckview> findAllPublicOrderbyCardcountDesc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.likecount ASC")
    Page<Extendedcarddeckview> findAllPublicOrderbyLikecountAsc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.favoritecount ASC")
    Page<Extendedcarddeckview> findAllPublicOrderbyFavoritecountAsc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 ORDER BY x.cardcount ASC")
    Page<Extendedcarddeckview> findAllPublicOrderbyCardcountAsc(Pageable pageable);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.id IN (SELECT y.carddeckid FROM Userhasfavorite y WHERE y.flashyuserid = :id)")
    List<Extendedcarddeckview> getAllFavoritesByFlashyuserId(long id);
}

