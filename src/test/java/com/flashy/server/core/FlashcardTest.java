package com.flashy.server.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlashcardTest {

    String question;
    String answer;

    // Set up the test
    @BeforeEach
    public void setUp() {
        question = "question";
        answer = "answer";
    }

    // Test the constructor
    @Test
    public void testFlashcard() {
        Flashcard flashcard = new Flashcard(question, answer);
        assertEquals(question, flashcard.getQuestion());
        assertEquals(answer, flashcard.getAnswer());
    }

    // Test the setQuestion method
    @Test
    public void testSetQuestion() {
        Flashcard flashcard = new Flashcard(question, answer);
        flashcard.setQuestion("new Question");
        assertEquals("new Question", flashcard.getQuestion());
    }

    // Test the setAnswer method
    @Test
    public void testSetAnswer() {
        Flashcard flashcard = new Flashcard(question, answer);
        flashcard.setAnswer("new Answer");
        assertEquals("new Answer", flashcard.getAnswer());
    }

}
