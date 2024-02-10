package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    String username;
    String password;

    List<FlashcardDeck> decks = new ArrayList<>();
    List<FlashcardDeck> favDecks = new ArrayList<>();

    // Set up the test
    @BeforeEach
    public void setUp() {
        username = "username";
        password = "password";
        for (int i = 0; i < 10; i++) {
            decks.add(new FlashcardDeck("Deck " + i + 1));
        }
    }

    // Test the constructor
    @Test
    public void testUser() {
        User user = new User(username, password);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(0, user.getDecks().size());
    }

    // Test makeNewDeck
    @Test
    public void testMakeNewDeck() {
        User user = new User(username, password);
        user.makeNewDeck("Deck 1");
        assertEquals(1, user.getDecks().size());
    }

    // Test the deleteDeck method
    @Test
    public void testDeleteDeck() {
        User user = new User(username, password);
        user.makeNewDeck("Deck 1");
        user.deleteDeck("Deck 1");
        assertEquals(0, user.getDecks().size());
    }
}
