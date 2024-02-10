package com.flashy.server.core;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;

    private List<FlashcardDeck> decks;
    private List<FlashcardDeck> favDecks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.decks = new ArrayList<FlashcardDeck>();
        this.favDecks = new ArrayList<FlashcardDeck>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<FlashcardDeck> getDecks() {
        return decks;
    }

    public List<FlashcardDeck> getFavDecks() {
        return favDecks;
    }

    public void makeNewDeck(String deckName) {
        decks.add(new FlashcardDeck(deckName));
    }

    public void deleteDeck(String deckName) {
        for (FlashcardDeck deck : decks) {
            if (deck.getName().equals(deckName)) {
                decks.remove(deck);
                break;
            }
        }
    }

}
