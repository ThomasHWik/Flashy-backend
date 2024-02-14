package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlashcardTest {

    String question;
    String answer;
    int cardID;
    Flashcard flashcard;

    // Set up the test
    @BeforeEach
    public void setUp() {
        question = "question";
        answer = "answer";
        cardID = 1;
        flashcard = new Flashcard(question, answer, cardID);
    }

    // Test the constructor
    @Test
    public void testFlashcard() {
        assertEquals(question, flashcard.getQuestion());
        assertEquals(answer, flashcard.getAnswer());
        assertEquals(cardID, flashcard.getCardID());
    }

    // Test the getQuestion method
    @Test
    public void testGetQuestion() {
        assertEquals("question", flashcard.getQuestion());
    }

    // Test the getAnswer method
    @Test
    public void testGetAnswer() {
        assertEquals("answer", flashcard.getAnswer());
    }

    // Test the getCardID method
    @Test
    public void testGetCardID() {
        assertEquals(1, flashcard.getCardID());
    }

    // Test the setQuestion method
    @Test
    public void testSetQuestion() {
        flashcard.setQuestion("new Question");
        assertEquals("new Question", flashcard.getQuestion());
    }

    // Test the setAnswer method
    @Test
    public void testSetAnswer() {
        flashcard.setAnswer("new Answer");
        assertEquals("new Answer", flashcard.getAnswer());
    }

    // Test the setCardID method
    @Test
    public void testSetCardID() {
        flashcard.setCardID(2);
        assertEquals(2, flashcard.getCardID());
    }

}
