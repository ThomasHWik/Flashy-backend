package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlashcardDeckTest {

    FlashcardDeck deck;

    // Set up the test
    @BeforeEach
    public void setUp() {
        deck = new FlashcardDeck("Deck 1");
    }

    // Test the constructor
    @Test
    public void testFlashcardDeck() {
        assertEquals("Deck 1", deck.getName());
    }

    // Test the addCard method
    @Test
    public void testAddCard() {
        deck.addCard("Question", "Answer");
        assertEquals(1, deck.getCards().size());
    }

    // Test the deleteCard method
    @Test
    public void testRemoveCard() {
        deck.addCard("Question", "Answer");
        deck.removeCard("Question");
        assertEquals(0, deck.getCards().size());
    }
}
