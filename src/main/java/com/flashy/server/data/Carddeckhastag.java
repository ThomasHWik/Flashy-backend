package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Carddeckhastag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private int carddeckid;

    @NotBlank
    private int tagid;

    public Carddeckhastag() {

    }

    public Carddeckhastag(int carddeckid, int tagid) {
        this.carddeckid = carddeckid;
        this.tagid = tagid;
    }

}

