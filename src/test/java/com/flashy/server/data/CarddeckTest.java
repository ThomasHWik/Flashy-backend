package com.flashy.server.data;

import com.flashy.server.core.FlashcardDeck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarddeckTest {

    @Test
    public void testInitialize() {
        new Carddeck();
        new Carddeck(1, "", "", 0, 1);
    }

    @Test
    public void testGettersAndSetters() {

        Carddeck carddeck = new Carddeck();
        carddeck.setUuid("uuid");
        carddeck.setFlashyuser_id(1);
        carddeck.setIsprivate(1);
        carddeck.setTitle("name");

        assertEquals("uuid", carddeck.getUuid());
        assertEquals(1, carddeck.getFlashyuser_id());
        assertEquals(1, carddeck.getIsprivate());
        assertEquals("name", carddeck.getTitle());


    }

    @Test
    public void testEquals() {
        Carddeck carddeck = new Carddeck();
        carddeck.setUuid("uuid");
        carddeck.setFlashyuser_id(1);
        carddeck.setIsprivate(1);
        carddeck.setTitle("name");

        Carddeck carddeck2 = new Carddeck();
        carddeck2.setUuid("uuid");
        carddeck2.setFlashyuser_id(1);
        carddeck2.setIsprivate(1);
        carddeck2.setTitle("name");

        assertEquals(carddeck, carddeck2);
    }

    @Test
    public void testHashcode() {
        Carddeck carddeck = new Carddeck();
        carddeck.setUuid("uuid");
        carddeck.setFlashyuser_id(1);
        carddeck.setIsprivate(1);
        carddeck.setTitle("name");

        Carddeck carddeck2 = new Carddeck();
        carddeck2.setUuid("uuid");
        carddeck2.setFlashyuser_id(1);
        carddeck2.setIsprivate(1);
        carddeck2.setTitle("name");

        assertEquals(carddeck.hashCode(), carddeck2.hashCode());
    }

}
