package com.flashy.server.api;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;


    @PostMapping("/create")
    public HttpStatus createCardDeck(@RequestBody FlashcardDeck flashcardDeck) {
        System.out.println(flashcardDeck);
        boolean success = flashcardService.createCarddeck(flashcardDeck);
        return success ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
