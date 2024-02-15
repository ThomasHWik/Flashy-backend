package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class FlashcardTest {

    String question;
    String answer;
    int cardID;
    FlashcardDTO flashcard;

    // Set up the test
    @BeforeEach
    public void setUp() {
        question = "question";
        answer = "answer";
        flashcard = new FlashcardDTO(question, answer, UUID.randomUUID().toString());
    }

    // Test the constructor
    @Test
    public void testFlashcard() {

        FlashcardDTO flashcard = new FlashcardDTO(question, answer, UUID.randomUUID().toString());

        assertEquals(question, flashcard.getQuestion());
        assertEquals(answer, flashcard.getAnswer());
        assertEquals(cardID, flashcard.getUuid());
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
        assertEquals(1, flashcard.getUuid());
    }

    // Test the setQuestion method
    @Test
    public void testSetQuestion() {

        FlashcardDTO flashcard = new FlashcardDTO(question, answer, UUID.randomUUID().toString());

        flashcard.setQuestion("new Question");
        assertEquals("new Question", flashcard.getQuestion());
    }

    // Test the setAnswer method
    @Test
    public void testSetAnswer() {

        FlashcardDTO flashcard = new FlashcardDTO(question, answer, UUID.randomUUID().toString());

        flashcard.setAnswer("new Answer");
        assertEquals("new Answer", flashcard.getAnswer());
    }

    // Test the setCardID method
    @Test
    public void testSetCardID() {
        flashcard.setUuid("2");
        assertEquals(2, flashcard.getUuid());
    }

}
