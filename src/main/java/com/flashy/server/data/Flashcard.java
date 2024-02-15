package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    @NotBlank
    private int carddeck_id;

    @NotBlank
    private String uuid;


    public Flashcard(int id, String question, String answer, int carddeck_id, String uuid) {
        this.question = question;
        this.answer = answer;
        this.id = id;
        this.uuid = uuid;
        this.carddeck_id = carddeck_id;

    }

    public Flashcard(String question, String answer, String uuid, int carddeck_id) {
        this.question = question;
        this.answer = answer;
        this.uuid = uuid;
        this.carddeck_id = carddeck_id;
    }

    public Flashcard() {

    }
}

