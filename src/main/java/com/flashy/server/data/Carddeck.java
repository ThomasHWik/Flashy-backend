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

    public Carddeck() {

    }
    public Carddeck(int id, String uuid, String title, int isprivate) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.isprivate = isprivate;
    }

    public Carddeck(String uuid, String title, int isprivate) {
        this.uuid = uuid;
        this.title = title;
        this.isprivate = isprivate;
    }
}