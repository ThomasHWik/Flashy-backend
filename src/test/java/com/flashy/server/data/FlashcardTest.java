package com.flashy.server.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlashcardTest {

    @Test
    public void testInitialize() {
        new Flashcard();
    }
    @Test
    public void testGettersAndSetters() {

        Flashcard flashcard = new Flashcard();
        flashcard.setAnswer("answer");
        flashcard.setQuestion("question");
        flashcard.setUuid("uuid");
        flashcard.setCarddeckid(1);

        assertEquals("answer", flashcard.getAnswer());
        assertEquals("question", flashcard.getQuestion());
        assertEquals("uuid", flashcard.getUuid());
        assertEquals(1, flashcard.getCarddeckid());
    }

    @Test
    public void testEquals() {
        Flashcard flashcard = new Flashcard();
        flashcard.setAnswer("answer");
        flashcard.setQuestion("question");
        flashcard.setUuid("uuid");
        flashcard.setCarddeckid(1);

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setAnswer("answer");
        flashcard2.setQuestion("question");
        flashcard2.setUuid("uuid");
        flashcard2.setCarddeckid(1);

        assertEquals(flashcard, flashcard2);
    }

    @Test
    public void testHashcode() {
        Flashcard flashcard = new Flashcard();
        flashcard.setAnswer("answer");
        flashcard.setQuestion("question");
        flashcard.setUuid("uuid");
        flashcard.setCarddeckid(1);

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setAnswer("answer");
        flashcard2.setQuestion("question");
        flashcard2.setUuid("uuid");
        flashcard2.setCarddeckid(1);

        assertEquals(flashcard.hashCode(), flashcard2.hashCode());
    }


}
