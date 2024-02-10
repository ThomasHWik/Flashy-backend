package com.flashy.server.core;

import java.util.ArrayList;
import java.util.List;

public class FlashcardDeck {

    private String name;
    private List<Flashcard> cards = new ArrayList<>();

    public FlashcardDeck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCard(String question, String answer) {
        this.cards.add(new Flashcard(question, answer));
    }

    public void removeCard(String question) {
        for (Flashcard card : cards) {
            if (card.getQuestion().equals(question)) {
                this.cards.remove(card);
                break;
            }
        }
    }

    public List<Flashcard> getCards() {
        return cards;
    }

}
