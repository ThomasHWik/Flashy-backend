package com.flashy.server.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flashy.server.core.CommentDTO;
import com.flashy.server.core.FlashcardDeck;
import com.flashy.server.core.LoginResponseDTO;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashcard;
import com.flashy.server.exceptions.InvalidLoginException;
import com.flashy.server.service.CommentService;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.FlashyuserService;
import com.flashy.server.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/create")
    public ResponseEntity<CommentDTO> loginUser(@RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));
            CommentDTO resComment = commentService.createComment(commentDTO, username);

            if (resComment != null) {
                return new ResponseEntity<>(resComment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/{carddeckuuid}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable String carddeckuuid, @RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));
            List<CommentDTO> comments = commentService.getComments(username, carddeckuuid);
            if (comments != null) {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteComment(@PathVariable String uuid, @RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.substring(7));
            boolean res = commentService.deleteComment(username, uuid);
            if (res) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Missing credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
