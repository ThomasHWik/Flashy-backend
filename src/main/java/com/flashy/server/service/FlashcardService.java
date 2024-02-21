package com.flashy.server.service;

import com.flashy.server.core.CarddeckDTO;
import com.flashy.server.core.CarddeckListDTO;
import com.flashy.server.core.FlashcardDTO;
import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.repository.CarddeckRepository;
import com.flashy.server.repository.FlashcardRepository;
import com.flashy.server.repository.FlashyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FlashcardService {

   @Autowired
   private FlashcardRepository flashcardRepository;

   @Autowired
   private CarddeckRepository carddeckRepository;

   @Autowired
   private FlashyuserRepository flashyuserRepository;

    private boolean createFlashcards(FlashcardDeck flashcardDeck, int carddeckId) {
        try {
            flashcardRepository.saveAll(flashcardDeck.getCards().stream().map(x -> new Flashcard(x.getQuestion(), x.getAnswer(), UUID.randomUUID().toString(), carddeckId)).toList());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String createCarddeck(FlashcardDeck flashcardDeck, String username) {

            Flashyuser user = flashyuserRepository.getFirstByUsername(username);
            if (user == null) {
                return null;
            } else {
                Carddeck deck = carddeckRepository.save(new Carddeck(UUID.randomUUID().toString(), flashcardDeck.getName(), flashcardDeck.getIsprivate(), user.getId()));

                boolean success = createFlashcards(flashcardDeck, deck.getId());
                if (!success) {
                    carddeckRepository.deleteById(deck.getId());
                    return null;
                }
                return deck.getUuid();
            }
    }

    public CarddeckDTO getCarddeck(String uuid) {
            Carddeck deck = carddeckRepository.getFirstByUuid(uuid);
            if (deck != null) {
                List<Flashcard> cards = flashcardRepository.findAllByCarddeckId(deck.getId());
                List<FlashcardDTO> dtocards = cards.stream().map(x -> new FlashcardDTO(x.getQuestion(), x.getAnswer(), x.getUuid())).toList();
                Flashyuser user = flashyuserRepository.getById(deck.getFlashyuser_id());
                return new CarddeckDTO(deck.getTitle(), dtocards, deck.getIsprivate(), deck.getUuid(), user != null ? user.getUsername() : "");
            }
            return null;
    }

    public CarddeckListDTO getUserDecks(String username, Boolean isAuthorized) {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null) {
            List<Carddeck> storedDecks = isAuthorized ? carddeckRepository.getAllByFlashyuser_idEqualsAndAuthorized(dbUser.getId()) : carddeckRepository.getAllByFlashyuser_idEqualsAndNotAuthorized(dbUser.getId());
            List<CarddeckDTO> dtoDecks = storedDecks.stream().map(x -> new CarddeckDTO(x.getTitle(), null, x.getIsprivate(), x.getUuid(), username)).toList();
            return new CarddeckListDTO(username, dtoDecks, isAuthorized ? 1 : 0);
        } else {
            return null;
        }

    }

    @Transactional
    public boolean editCarddeck(FlashcardDeck flashcardDeck, String username) {


        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(flashcardDeck.getUuid());
        if (dbDeck == null) {


            return false;
        }
        System.out.println(dbDeck.getIsprivate());
        dbDeck.setTitle(flashcardDeck.getName());
        if (dbUser != null && (dbUser.getIsadmin() == 1 || dbDeck.getFlashyuser_id() == dbUser.getId())) {
            dbDeck.setIsprivate(flashcardDeck.getIsprivate());
        } else if (dbDeck.getIsprivate() == 1) {
            // return false only if the deck is private and is being edited by a person not owner or admin
                return false;
        }



        carddeckRepository.save(dbDeck);
        flashcardRepository.deleteByCarddeckId(dbDeck.getId());

        return createFlashcards(flashcardDeck, dbDeck.getId());
    }


    @Transactional
    public boolean deleteCarddeck(String uuid, String username) {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);

        if ((dbUser != null && dbDeck != null) && (dbUser.getIsadmin() == 1 || dbDeck.getFlashyuser_id() == dbUser.getId())) {
            flashcardRepository.deleteByCarddeckId(dbDeck.getId());
            carddeckRepository.deleteById(dbDeck.getId());
            return true;
        } else {
            return false;
        }
    }
}
