package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Userhaslike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private int flashyuserid;

    @NotBlank
    private int carddeckid;

    public Userhaslike() {

    }

    public Userhaslike(int flashyuserid, int carddeckid) {
        this.flashyuserid = flashyuserid;
        this.carddeckid = carddeckid;
    }

}
