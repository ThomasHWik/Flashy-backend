package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Userhasfavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private int flashyuserid;

    @NotBlank
    private int carddeckid;

    public Userhasfavorite() {

    }

    public Userhasfavorite(int id, int flashyuserid, int carddeckid) {
        this.id = id;
        this.flashyuserid = flashyuserid;
        this.carddeckid = carddeckid;
    }

    public Userhasfavorite(int flashyuserid, int carddeckid) {
        this.flashyuserid = flashyuserid;
        this.carddeckid = carddeckid;
    }

}
