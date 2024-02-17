package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Carddeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String uuid;

    @NotBlank
    private String title;

    @NotBlank
    private int isprivate;

    @NotBlank
    private int flashyuser_id;


    public Carddeck() {

    }
    public Carddeck(int id, String uuid, String title, int isprivate, int flashyuser_id) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.isprivate = isprivate;
        this.flashyuser_id = flashyuser_id;
    }

    public Carddeck(String uuid, String title, int isprivate, int flashyuser_id) {
        this.uuid = uuid;
        this.title = title;
        this.isprivate = isprivate;
        this.flashyuser_id = flashyuser_id;
    }
}