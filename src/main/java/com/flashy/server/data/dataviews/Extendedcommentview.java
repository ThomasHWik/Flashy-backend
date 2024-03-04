package com.flashy.server.data.dataviews;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Extendedcommentview {

    @Id
    private int id;

    private String uuid;

    private int flashyuserid;

    private String username;

    private String carddeckuuid;

    private String comment;

    private LocalDateTime createdat;

    public Extendedcommentview() {

    }


    public Extendedcommentview(int id, String uuid, int flashyuserid, String username, String carddeckuuid, String comment, LocalDateTime createdat) {
        this.id = id;
        this.uuid = uuid;
        this.flashyuserid = flashyuserid;
        this.username = username;
        this.carddeckuuid = carddeckuuid;
        this.comment = comment;
        this.createdat = createdat;
    }




}
