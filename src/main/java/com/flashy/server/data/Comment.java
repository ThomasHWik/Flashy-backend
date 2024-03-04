package com.flashy.server.data;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String comment;

    @NotBlank
    private int flashyuserid;

    @NotBlank
    private int carddeckid;

    @NotBlank
    private String uuid;


    private LocalDateTime createdat;

    public Comment() {

    }

    public Comment(String comment, int flashyuserid, int carddeckid, String uuid, LocalDateTime createdat) {
        this.comment = comment;
        this.flashyuserid = flashyuserid;
        this.carddeckid = carddeckid;
        this.uuid = uuid;
        this.createdat = createdat;

    }

    public Comment(String comment, int flashyuserid, int carddeckid, String uuid ) {
        this.id = id;
        this.comment = comment;
        this.flashyuserid = flashyuserid;
        this.carddeckid = carddeckid;
        this.uuid = uuid;
        this.createdat = LocalDateTime.now();
        System.out.println("Comment created at: " + this.createdat);


    }


}
