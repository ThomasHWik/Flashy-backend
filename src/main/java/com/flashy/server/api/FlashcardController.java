package com.flashy.server.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flashy.server.core.CarddeckDTO;
import com.flashy.server.core.CarddeckListDTO;
import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.data.Flashcard;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.JWTService;
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

    @Autowired
    private JWTService jwtService;


    @PostMapping("/create")
    public ResponseEntity<String> createCardDeck(@RequestBody FlashcardDeck flashcardDeck, @RequestHeader("Authorization") final String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));

            String uuid = flashcardService.createCarddeck(flashcardDeck, username);
            if (uuid != null) {
                return new ResponseEntity<>(uuid, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/edit")
    public ResponseEntity<String> editCardDeck(@RequestBody FlashcardDeck flashcardDeck, @RequestHeader("Authorization") final String token) {
        String username;
        try {
            username = jwtService.getUsernameFromToken(token.substring(7));
        } catch (JWTVerificationException e) {
            username = null;
        }
        try {
            boolean success = flashcardService.editCarddeck(flashcardDeck, username);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> editCardDeck(@PathVariable String uuid, @RequestHeader("Authorization") final String token) {

        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));

            boolean success = flashcardService.deleteCarddeck(uuid, username);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/id/{uuid}")
    public ResponseEntity<CarddeckDTO> getCarddeckByUuid(@PathVariable String uuid) {
        System.out.println(uuid);
        try {
            CarddeckDTO res = flashcardService.getCarddeck(uuid);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<CarddeckListDTO> getCarddecksFromUser(@PathVariable String username, @RequestHeader("Authorization") final String token) {
        boolean isAuthorized;
        try {
            isAuthorized = jwtService.checkUsernameCorrespondsWithToken(username, token.substring(7));
        } catch (JWTVerificationException e) {
            isAuthorized = false;
        }

        try {
            CarddeckListDTO res = flashcardService.getUserDecks(username, isAuthorized);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
