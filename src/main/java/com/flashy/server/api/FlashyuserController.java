package com.flashy.server.api;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashcard;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.FlashyuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class FlashyuserController {

    @Autowired
    private FlashyuserService flashyuserService;


    @PostMapping("/registrer")
    public HttpStatus registrerUser(@RequestBody UserDTO user) {
        System.out.println(user);
        try {
            boolean success = flashyuserService.registrerUser(user);
            return success ? HttpStatus.CREATED : HttpStatus.FORBIDDEN;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }



    @PostMapping("/login")
    public HttpStatus loginUser(@RequestBody UserDTO user) {
        System.out.println(user);
        try {
            boolean success = flashyuserService.loginUser(user);
            return success ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
