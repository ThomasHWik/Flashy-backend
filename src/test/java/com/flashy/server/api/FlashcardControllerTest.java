package com.flashy.server.api;

import com.flashy.server.AbstractTest;
import com.flashy.server.core.*;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.FlashyuserService;
import com.flashy.server.service.JWTService;

import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlashcardControllerTest extends AbstractTest {
    @Autowired
    FlashcardService flashcardService;

    @Autowired
    JWTService jwtService;

    FlashcardDeck flashcardDeck;

    String uuid2;

    @BeforeAll
    public void setUp() {
        super.setUp();

        flashcardDeck = new FlashcardDeck();
        flashcardDeck.setName(UUID.randomUUID().toString());
        List<FlashcardDTO> flashcards = new ArrayList<>();
        flashcards.add(new FlashcardDTO("TestQuestion1", "TestAnswer1", ""));
        flashcards.add(new FlashcardDTO("TestQuestion2", "TestAnswer2", ""));
        flashcardDeck.setCards(flashcards);
    }

    @Test
    public void testCreateCarddeck() throws Exception {
        String uri = "/flashcard/create";

        // save public deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // get deck
        ExtendedCarddeckListDTO decks = flashcardService.getUserDecks("admin", true);
        String uuid = null;
        for (ExtendedCarddeckDTO deck : decks.getCarddecks()) {
            if (deck.getName().equals(flashcardDeck.getName())) {
                uuid = deck.getUuid();
                break;
            }
        }
        if (uuid == null) {
            fail("Deck not found");
        }

        // get specific deck
        ExtendedCarddeckDTO storedDeck = flashcardService.getCarddeck("admin", uuid);
        assertNotNull(storedDeck);
        assertEquals(flashcardDeck.getName(), storedDeck.getName());
        assertEquals(flashcardDeck.getCards().size(), storedDeck.getCards().size());
        assertEquals(flashcardDeck.getIsprivate(), storedDeck.getIsprivate());
        for (int i = 0; i < flashcardDeck.getCards().size(); i++) {
            assertEquals(flashcardDeck.getCards().get(i).getQuestion(), storedDeck.getCards().get(i).getQuestion());
            assertEquals(flashcardDeck.getCards().get(i).getAnswer(), storedDeck.getCards().get(i).getAnswer());
        }

        // delete deck
        flashcardService.deleteCarddeck(uuid, "admin");

        // save private deck
        flashcardDeck.setIsprivate(1);
        flashcardDeck.setName(UUID.randomUUID().toString());
        inputJson = super.mapToJson(flashcardDeck);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // get deck
        decks = flashcardService.getUserDecks("admin", true);
        String uuid1 = null;
        for (ExtendedCarddeckDTO deck : decks.getCarddecks()) {
            if (deck.getName().equals(flashcardDeck.getName())) {
                uuid1 = deck.getUuid();
                break;
            }
        }

        if (uuid1 == null) {
            fail("Deck not found");
        }

        // get specific deck
        storedDeck = flashcardService.getCarddeck("admin", uuid1);
        assertNotNull(storedDeck);
        assertEquals(flashcardDeck.getName(), storedDeck.getName());
        assertEquals(flashcardDeck.getCards().size(), storedDeck.getCards().size());
        assertEquals(flashcardDeck.getIsprivate(), storedDeck.getIsprivate());

        for (int i = 0; i < flashcardDeck.getCards().size(); i++) {
            assertEquals(flashcardDeck.getCards().get(i).getQuestion(), storedDeck.getCards().get(i).getQuestion());
            assertEquals(flashcardDeck.getCards().get(i).getAnswer(), storedDeck.getCards().get(i).getAnswer());
        }

        // delete deck
        flashcardService.deleteCarddeck(uuid1, "admin");

        // create without token
        inputJson = super.mapToJson(flashcardDeck);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer ")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // create with invalid token
        inputJson = super.mapToJson(flashcardDeck);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer " + jwtService.getToken(UUID.randomUUID().toString()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);
    }

    @Test
    public void testEditCardDeck() throws Exception {
        String uri = "/flashcard/edit";

        // save public deck
        flashcardDeck.setIsprivate(0);
        flashcardDeck.setName(UUID.randomUUID().toString());
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = null;

        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // get deck
        ExtendedCarddeckListDTO decks = flashcardService.getUserDecks("admin", true);
        String uuid = null;
        for (ExtendedCarddeckDTO deck : decks.getCarddecks()) {
            if (deck.getName().equals(flashcardDeck.getName())) {
                uuid = deck.getUuid();
                break;
            }
        }
        if (uuid == null) {
            fail("Deck not found");
        }

        // edit deck with permissions
        uri = "/flashcard/edit";
        flashcardDeck.setName(UUID.randomUUID().toString());
        flashcardDeck.setCards(flashcardDeck.getCards().subList(0, 1));
        flashcardDeck.setIsprivate(1);
        flashcardDeck.setUuid(uuid);
        inputJson = super.mapToJson(flashcardDeck);
        System.out.println(flashcardDeck);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // check deck
        ExtendedCarddeckDTO d = flashcardService.getCarddeck("admin", uuid);
        assertEquals(flashcardDeck.getName(), d.getName());
        assertEquals(flashcardDeck.getCards().size(), d.getCards().size());
        assertEquals(flashcardDeck.getIsprivate(), d.getIsprivate());

        // edit deck without permissions
        uri = "/flashcard/edit";
        flashcardDeck.setName(UUID.randomUUID().toString());
        flashcardDeck.setCards(flashcardDeck.getCards().subList(0, 1));
        flashcardDeck.setIsprivate(1);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .header("Authorization", "Bearer " + jwtService.getToken("user"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // edit deck without token
        uri = "/flashcard/edit";
        flashcardDeck.setName(UUID.randomUUID().toString());
        flashcardDeck.setCards(flashcardDeck.getCards().subList(0, 1));
        flashcardDeck.setIsprivate(1);
        mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .header("Authorization", "Bearer ")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // delete deck
        flashcardService.deleteCarddeck(uuid, "admin");

    }

    @Test
    public void testGetDeck() throws Exception {

        // create deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String uuid = mvcResult.getResponse().getContentAsString();
        flashcardDeck.setUuid(uuid);

        // get deck
        String uri = "/flashcard/id/" + flashcardDeck.getUuid();
        mvcResult = mvc.perform(
                MockMvcRequestBuilders.get(uri).header("Authorization", "Bearer " + jwtService.getToken("admin")))
                .andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        ExtendedCarddeckDTO content1 = super.mapFromJson(mvcResult.getResponse().getContentAsString(),
                ExtendedCarddeckDTO.class);

        assertEquals(flashcardDeck.getName(), content1.getName());
        assertEquals(flashcardDeck.getCards().size(), content1.getCards().size());
        assertEquals(flashcardDeck.getIsprivate(), content1.getIsprivate());

        // get deck with invalid uuid
        uri = "/flashcard/id/" + UUID.randomUUID().toString();
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        // delete
        flashcardService.deleteCarddeck(flashcardDeck.getUuid(), "admin");

    }

    @Test
    public void testDeleteDeck() throws Exception {
        // create deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                    .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String uuid = mvcResult.getResponse().getContentAsString();
        flashcardDeck.setUuid(uuid);

        // delete deck with invalid token
        String uri = "/flashcard/delete/" + flashcardDeck.getUuid();
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .header("Authorization", "Bearer " + "invalidToken")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // delete deck with no token
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .header("Authorization ", "Bearer ")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        // delete deck with invalid uuid

        mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/flashcard/delete/" + UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // delete deck with valid token
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    public void testGetUserDecks() throws Exception {
        // create deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String uuid = mvcResult.getResponse().getContentAsString();
        flashcardDeck.setUuid(uuid);

        // get decks
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flashcard/user/admin")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        ExtendedCarddeckListDTO content = super.mapFromJson(mvcResult.getResponse().getContentAsString(),
                ExtendedCarddeckListDTO.class);
        assertEquals(1, content.getCarddecks().size());

        assertEquals(flashcardDeck.getName(), content.getCarddecks().get(0).getName());
        assertEquals(flashcardDeck.getIsprivate(), content.getCarddecks().get(0).getIsprivate());
        assertEquals(flashcardDeck.getUuid(), content.getCarddecks().get(0).getUuid());

        // with invalid token
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flashcard/user/admin")
                .header("Authorization", "Bearer " + "invalidToken")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ExtendedCarddeckListDTO.class);
        assertEquals(1, content.getCarddecks().size());

        // create private deck
        flashcardDeck.setIsprivate(1);
        inputJson = super.mapToJson(flashcardDeck);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        uuid2 = mvcResult.getResponse().getContentAsString();

        // get decks with no token
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flashcard/user/admin")
                .header("Authorization", "Bearer ")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ExtendedCarddeckListDTO.class);
        assertEquals(1, content.getCarddecks().size());

        // get decks with valid token
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flashcard/user/admin")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        content = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ExtendedCarddeckListDTO.class);
        assertEquals(2, content.getCarddecks().size());

        // get with invalid username
        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/flashcard/user/" + UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        // delete decks
        flashcardService.deleteCarddeck(uuid, "admin");
        flashcardService.deleteCarddeck(uuid2, "admin");

    }

    @Test
    public void testAddFavorite() throws Exception {
        // create deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String uuid = mvcResult.getResponse().getContentAsString();
        flashcardDeck.setUuid(uuid);

        // add favorite
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/favorite/add/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // add favorite with invalid token
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/favorite/add/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer " + "invalidToken")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // add favorite with no token
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/favorite/add/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer ")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // add favorite with invalid uuid
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/favorite/add/" + UUID.randomUUID().toString())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        // delete decks
        flashcardService.deleteCarddeck(uuid, "admin");

    }

    @Test
    public void testRemoveFavorite() throws Exception {
        // create deck
        flashcardDeck.setIsprivate(0);
        String inputJson = super.mapToJson(flashcardDeck);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/create")
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String uuid = mvcResult.getResponse().getContentAsString();
        flashcardDeck.setUuid(uuid);

        // add favorite
        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/flashcard/favorite/add/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // remove favorite
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/flashcard/favorite/remove/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer " + jwtService.getToken("admin"))).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // remove favorite with invalid token
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/flashcard/favorite/remove/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer " + "invalidToken")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // remove favorite with no token
        mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/flashcard/favorite/remove/" + flashcardDeck.getUuid())
                .header("Authorization", "Bearer ")).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

        // remove favorite with invalid uuid
        mvcResult = mvc
                .perform(MockMvcRequestBuilders.delete("/flashcard/favorite/remove/" + UUID.randomUUID().toString())
                        .header("Authorization", "Bearer " + jwtService.getToken("admin")))
                .andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        // delete decks
        flashcardService.deleteCarddeck(uuid, "admin");

    }

    @AfterThrowing
    public void deleteDeck() {
        flashcardService.deleteCarddeck(flashcardDeck.getUuid(), "admin");
        flashcardService.deleteCarddeck(uuid2, "admin");
    }

}
