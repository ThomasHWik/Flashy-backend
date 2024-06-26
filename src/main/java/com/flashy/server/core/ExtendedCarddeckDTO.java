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
    private int iseditable;

    private List<CommentDTO> comments;
    private List<FlashcardDTO> flashcards;
    private int isfavorited;
    private int isliked;

    private List<String> tags;

    public ExtendedCarddeckDTO() {

    }

    public ExtendedCarddeckDTO(String name, List<FlashcardDTO> cards, int isprivate, String uuid, String username,
            long cardcount, long likecount, long favoritecount, List<String> tags) {
        this.name = name;
        this.cards = cards;
        this.uuid = uuid;
        this.isprivate = isprivate;
        this.username = username;
        this.cardcount = cardcount;
        this.likecount = likecount;
        this.favoritecount = favoritecount;
        this.tags = tags;

    }
}