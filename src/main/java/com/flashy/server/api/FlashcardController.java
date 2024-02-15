package com.flashy.server.api;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;


    @PostMapping("/create")
    public ResponseEntity<String> createCardDeck(@RequestBody FlashcardDeck flashcardDeck) {
        System.out.println(flashcardDeck);
        try {
            boolean success = flashcardService.createCarddeck(flashcardDeck);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
