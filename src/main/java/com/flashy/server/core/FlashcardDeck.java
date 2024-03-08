package com.flashy.server.core;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


@Data
public class FlashcardDeck {


    private List<String> tags;
    private String name;
    private List<FlashcardDTO> cards;
    private String uuid;
    private int isprivate;
    private String username;

    public FlashcardDeck() {
        this.cards = new ArrayList<>();
    }


    public FlashcardDeck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.tags = new ArrayList<>();
    }
    public FlashcardDeck (String name, List<FlashcardDTO> cards, int isprivate, String uuid, String username) {
        this.name = name;
        this.cards = cards;
        this.uuid = uuid;
        this.isprivate = isprivate;
        this.username = username;
        this.cards = new ArrayList<>();
        this.tags = new ArrayList<>();

    }


    public FlashcardDeck (String name, List<FlashcardDTO> cards, int isprivate) {
        this.name = name;
        this.cards = cards;
        this.isprivate = isprivate;
        this.cards = new ArrayList<>();
        this.tags = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void addCard(String question, String answer, String uuid) {
        this.cards.add(new FlashcardDTO(question, answer, uuid));
    }


    public void swapCards(int index1, int index2) {
        FlashcardDTO tempIndex1 = cards.get((index1));
        cards.set(index1, cards.get(index2));
        cards.set(index2, tempIndex1);
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

}
