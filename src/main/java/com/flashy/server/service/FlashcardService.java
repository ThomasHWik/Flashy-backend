package com.flashy.server.service;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.repository.CarddeckRepository;
import com.flashy.server.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FlashcardService {

   @Autowired
   private FlashcardRepository flashcardRepository;

   @Autowired
   private CarddeckRepository carddeckRepository;

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
        try {
            int id = carddeckRepository.save(new Carddeck(UUID.randomUUID().toString(), flashcardDeck.getName(), flashcardDeck.getIsprivate())).getId();
            return createFlashcards(flashcardDeck, id);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
