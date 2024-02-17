package com.flashy.server.core;


import lombok.Data;

import java.util.List;
import java.util.Collections;


@Data
public class FlashcardDeck {


    private String name;
    private List<FlashcardDTO> cards;
    private String uuid;
    private int isprivate;
    private String username;

    public FlashcardDeck() {

    }


    public FlashcardDeck(String name) {
        this.name = name;
    }
    public FlashcardDeck (String name, List<FlashcardDTO> cards, int isprivate, String uuid, String username) {
        this.name = name;
        this.cards = cards;
        this.uuid = uuid;
        this.isprivate = isprivate;
        this.username = username;
    }


    public FlashcardDeck (String name, List<FlashcardDTO> cards, int isprivate) {
        this.name = name;
        this.cards = cards;
        this.isprivate = isprivate;
    }


    public String getName() {
        return name;
    }

    public void addCard(String question, String answer, String uuid) {
        this.cards.add(new FlashcardDTO(question, answer, uuid));
    }

    public void removeCard(String question) {
        for (FlashcardDTO card : cards) {
            if (card.getQuestion().equals(question)) {

                this.cards.remove(card);
                break;
            }
        }
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
