package com.flashy.server.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
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
            flashyuserService.registrerUser(user);
            return new ResponseEntity<>(new LoginResponseDTO(jwtService.getToken(user.getUsername()), 0), HttpStatus.OK);
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

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String username) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));

            boolean success = flashyuserService.deleteUser(username, tokenusername);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }  catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createAdmin(@RequestHeader("Authorization") final String token, @RequestBody UserDTO user) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));

            boolean success = flashyuserService.registerAdmin(user, username);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }  catch (InvalidLoginException e) {
            return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") final String token, @RequestBody UserDTO user) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));
            boolean success = flashyuserService.changePassword(user, username);
            if (success) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }  catch (InvalidLoginException e) {
            return new ResponseEntity<>("Invalid username", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/getall")
    public ResponseEntity<List<String>> getAllAdmins(@RequestHeader("Authorization") final String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));
            List<String> usernames = flashyuserService.getAllAdmins(username);
            if (usernames != null) {
                return new ResponseEntity<>(usernames, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
