package com.flashy.server.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class FlashcardDeck {

    private String name;
    private List<Flashcard> cards = new ArrayList<>();

    public FlashcardDeck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCard(String question, String answer, int cardID) {
        this.cards.add(new Flashcard(question, answer, cardID));
    }

    public void removeCard(int cardID) {
        for (Flashcard card : cards) {
            if (card.getCardID() == cardID) {
                this.cards.remove(card);
                break;
            }
        }
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public void swapCards(int index1, int index2) {
        Flashcard tempIndex1 = cards.get((index1));
        cards.set(index1, cards.get(index2));
        cards.set(index2, tempIndex1);
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

}
