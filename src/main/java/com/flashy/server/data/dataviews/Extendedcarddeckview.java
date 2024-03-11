package com.flashy.server.data.dataviews;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Extendedcarddeckview {

    @Id
    private int id;

    private String uuid;

    private String title;

    private int isprivate;

    private int flashyuserid;

    private String username;

    private long cardcount;

    private long likecount;

    private long favoritecount;

    private String taglist;

    private int iseditable;



    public Extendedcarddeckview() {

    }

    public Extendedcarddeckview(int id, String uuid, String title, int isprivate, int flashyuserid, String username,  long cardcount, long likecount, long favoritecount, String taglist) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.isprivate = isprivate;
        this.flashyuserid = flashyuserid;
        this.username = username;
        this.cardcount = cardcount;
        this.likecount = likecount;
        this.favoritecount = favoritecount;
        this.taglist = taglist;
    }




}
