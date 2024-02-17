package com.flashy.server.service;

import com.flashy.server.core.CarddeckDTO;
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

    public boolean createCarddeck(FlashcardDeck flashcardDeck) {

            Flashyuser user = flashyuserRepository.getFirstByUsername(flashcardDeck.getUsername());
            if (user == null) {
                return false;
            } else {
                int id = carddeckRepository.save(new Carddeck(UUID.randomUUID().toString(), flashcardDeck.getName(), flashcardDeck.getIsprivate(), user.getId())).getId();
                return createFlashcards(flashcardDeck, id);
            }


    }

    public CarddeckDTO getCarddeck(String uuid) {
            Carddeck deck = carddeckRepository.getFirstByUuid(uuid);
            if (deck != null) {
                List<Flashcard> cards = flashcardRepository.findAllByCarddeckId(deck.getId());
                System.out.println(cards.size());
                List<FlashcardDTO> dtocards = cards.stream().map(x -> new FlashcardDTO(x.getQuestion(), x.getAnswer(), x.getUuid())).toList();
                Flashyuser user = flashyuserRepository.getById(deck.getFlashyuser_id());
                return new CarddeckDTO(deck.getTitle(), dtocards, deck.getIsprivate(), deck.getUuid(), user != null ? user.getUsername() : "");
            }
            return null;
    }
}
