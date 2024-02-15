package com.flashy.server.api;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashcard;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.FlashyuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class FlashyuserController {

    @Autowired
    private FlashyuserService flashyuserService;


    @PostMapping("/registrer")
    public ResponseEntity<String> registrerUser(@RequestBody UserDTO user) {
        System.out.println(user);
        try {
            boolean success = flashyuserService.registrerUser(user);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }



    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO user) {
        System.out.println(user);
        try {
            boolean success = flashyuserService.loginUser(user);
            if (success){
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);

            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
