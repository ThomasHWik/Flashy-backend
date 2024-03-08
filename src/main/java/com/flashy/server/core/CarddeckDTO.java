package com.flashy.server.core;



import lombok.Data;

import java.util.List;
import java.util.Collections;


@Data
public class CarddeckDTO {


    private String name;
    private List<FlashcardDTO> cards;
    private String uuid;
    private int isprivate;
    private String username;

    private List<String> tags;

    public CarddeckDTO() {

    }


    public CarddeckDTO(String name) {
        this.name = name;
    }

    public CarddeckDTO(String name, List<FlashcardDTO> cards, int isprivate, String uuid, String username) {
        this.name = name;
        this.cards = cards;
        this.uuid = uuid;
        this.isprivate = isprivate;
        this.username = username;

    }
}