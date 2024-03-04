package com.flashy.server.core;


import lombok.Data;

import java.util.List;
import java.util.Collections;


@Data
public class ExtendedCarddeckDTO {


    private String name;
    private List<FlashcardDTO> cards;
    private String uuid;
    private int isprivate;
    private String username;
    private long cardcount;
    private long likecount;
    private long favoritecount;

    private List<CommentDTO> comments;
    private List<FlashcardDTO> flashcards;


    public ExtendedCarddeckDTO() {

    }
    public ExtendedCarddeckDTO(String name, List<FlashcardDTO> cards, int isprivate, String uuid, String username, long cardcount, long likecount, long favoritecount) {
        this.name = name;
        this.cards = cards;
        this.uuid = uuid;
        this.isprivate = isprivate;
        this.username = username;
        this.cardcount = cardcount;
        this.likecount = likecount;
        this.favoritecount = favoritecount;

    }
}