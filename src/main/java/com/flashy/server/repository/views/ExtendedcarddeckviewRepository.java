package com.flashy.server.repository.views;

import com.flashy.server.data.Carddeck;
import com.flashy.server.data.dataviews.Extendedcarddeckview;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public interface ExtendedcarddeckviewRepository extends JpaRepository<Extendedcarddeckview, Long> {

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.isprivate = 0 AND x.title LIKE %:searchquery%")
    Page<Extendedcarddeckview> findAllPublic(Pageable pageable, String searchquery);

    Extendedcarddeckview getFirstByUuid(String uuid);


    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY favoritecount DESC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyFavoritecountDesc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);

    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY cardcount DESC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyCardcountDesc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);

    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY likecount DESC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyLikecountDesc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);

    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY favoritecount ASC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyFavoritecountAsc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);

    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY cardcount ASC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyCardcountAsc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);

    @Query(value = "SELECT * FROM extendedcarddeckview WHERE isprivate = 0 AND title LIKE %:searchquery% AND :tagcount = (SELECT COUNT(DISTINCT value) FROM STRING_SPLIT(taglist, \',\') WHERE value IN :tags) ORDER BY likecount ASC", nativeQuery = true)
    Page<Extendedcarddeckview> findAllPublicOrderbyLikecountAsc(Pageable pageable, @Param("searchquery") String searchquery, @Param("tags") List<String> tags, @Param("tagcount") long tagcount);


    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.id IN (SELECT y.carddeckid FROM Userhasfavorite y WHERE y.flashyuserid = :id)")
    List<Extendedcarddeckview> getAllFavoritesByFlashyuserId(long id);


    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.flashyuserid = :id AND x.isprivate = 0")
    List<Extendedcarddeckview> getAllByFlashyuseridEqualsAndNotAuthorized(int id);

    @Query("SELECT x FROM Extendedcarddeckview x WHERE x.flashyuserid = :id")
    List<Extendedcarddeckview> getAllByFlashyuseridEqualsAndAuthorized(int id);

}

