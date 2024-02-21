package com.flashy.server.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlashyuserTest {

    @Test
    public void testInitialize() {
        new Flashyuser();
        new Flashyuser(1, "username", "password", 0);
    }

    @Test
    public void testGettersAndSetters() {
        Flashyuser flashyuser = new Flashyuser();
        flashyuser.setUsername("username");
        flashyuser.setPassword("password");
        flashyuser.setIsadmin(0);

        assertEquals("username", flashyuser.getUsername());
        assertEquals("password", flashyuser.getPassword());
        assertEquals(0, flashyuser.getIsadmin());
    }

    @Test
    public void testEquals() {
        Flashyuser flashyuser = new Flashyuser();
        flashyuser.setUsername("username");
        flashyuser.setPassword("password");
        flashyuser.setIsadmin(0);

        Flashyuser flashyuser2 = new Flashyuser();
        flashyuser2.setUsername("username");
        flashyuser2.setPassword("password");
        flashyuser2.setIsadmin(0);

        assertEquals(flashyuser, flashyuser2);
    }

    @Test
    public void testHashcode() {
        Flashyuser flashyuser = new Flashyuser();
        flashyuser.setUsername("username");
        flashyuser.setPassword("password");
        flashyuser.setIsadmin(0);

        Flashyuser flashyuser2 = new Flashyuser();
        flashyuser2.setUsername("username");
        flashyuser2.setPassword("password");
        flashyuser2.setIsadmin(0);

        assertEquals(flashyuser.hashCode(), flashyuser2.hashCode());
    }
}
