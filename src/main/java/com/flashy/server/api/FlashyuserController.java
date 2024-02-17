package com.flashy.server.api;

import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.core.LoginResponseDTO;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashcard;
import com.flashy.server.exceptions.InvalidLoginException;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.FlashyuserService;
import com.flashy.server.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class FlashyuserController {

    @Autowired
    private FlashyuserService flashyuserService;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registerUser(@RequestBody UserDTO user) {

        try {
            int isAdmin = flashyuserService.registrerUser(user);
            return new ResponseEntity<>(new LoginResponseDTO(jwtService.getToken(user.getUsername()), isAdmin), HttpStatus.OK);
        } catch (InvalidLoginException e) {
            return new ResponseEntity<>(new LoginResponseDTO("Invalid credentials", 0), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponseDTO("Internal error", 0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody UserDTO user) {
        try {
            int isAdmin = flashyuserService.loginUser(user);
            return new ResponseEntity<>(new LoginResponseDTO(jwtService.getToken(user.getUsername()), isAdmin), HttpStatus.OK);
        } catch (InvalidLoginException e) {
            return new ResponseEntity<>(new LoginResponseDTO("Invalid credentials", 0), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponseDTO("Internal error", 0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
