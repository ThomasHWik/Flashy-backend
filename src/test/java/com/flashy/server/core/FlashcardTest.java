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

    String uuid;

    // Set up the test
    @BeforeEach
    public void setUp() {
        question = "question";
        answer = "answer";
        uuid = UUID.randomUUID().toString();

        flashcard = new FlashcardDTO(question, answer, uuid);
    }

    // Test the constructor
    @Test
    public void testFlashcard() {

        FlashcardDTO flashcard = new FlashcardDTO(question, answer, uuid);

        assertEquals(question, flashcard.getQuestion());
        assertEquals(answer, flashcard.getAnswer());
        assertEquals(uuid, flashcard.getUuid());
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
        assertEquals(uuid, flashcard.getUuid());
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
        String uuid2 = "2";
        flashcard.setUuid(uuid2);
        assertEquals(uuid2, flashcard.getUuid());
    }

}
