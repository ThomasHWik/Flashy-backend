package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

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
        deck.addCard("Question", "Answer", String.valueOf(deck.getCards().size() + 1));
        assertEquals(1, deck.getCards().size());
    }

    // Test the swapCards method
    @Test
    public void testSwapCards() {
        for (int i = 0; i < 10; i++) {
            deck.addCard("Question" + i, "Answer" + i, Integer.toString(i));
        }
        deck.swapCards(3, 7);
        assertEquals(deck.getCards().get(3).getQuestion(), "Question7");
        assertEquals(deck.getCards().get(7).getQuestion(), "Question3");
        assertEquals(deck.getCards().get(3).getAnswer(), "Answer7");
        assertEquals(deck.getCards().get(7).getAnswer(), "Answer3");
        assertEquals(deck.getCards().get(3).getUuid(), "7");
        assertEquals(deck.getCards().get(7).getUuid(), "3");
    }

    // Test the shuffleCards method
    @Test
    public void testShuffleCards() {
        for (int i = 0; i < 10; i++) {
            deck.addCard("Question" + i, "Answer" + i,String.valueOf(i));
        }
        ArrayList<FlashcardDTO> originalOrder = new ArrayList<>(deck.getCards());
        deck.shuffleCards();
        ArrayList<FlashcardDTO> shuffledOrder = new ArrayList<>(deck.getCards());
        boolean orderChanged = false;
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (!originalOrder.get(i).equals(shuffledOrder.get(i))) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

}
