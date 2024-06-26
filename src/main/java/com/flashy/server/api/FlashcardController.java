package com.flashy.server.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flashy.server.core.*;
import com.flashy.server.data.dataviews.Extendedcarddeckview;
import com.flashy.server.service.FlashcardService;
import com.flashy.server.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flashy.server.core.ExtendedCarddeckDTO;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createCardDeck(@RequestBody FlashcardDeck flashcardDeck,
            @RequestHeader("Authorization") final String token) {
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
    public ResponseEntity<String> editCardDeck(@RequestBody FlashcardDeck flashcardDeck,
            @RequestHeader("Authorization") final String token) {
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
    public ResponseEntity<String> editCardDeck(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
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
    public ResponseEntity<ExtendedCarddeckDTO> getCarddeckByUuid(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));
            ExtendedCarddeckDTO res = flashcardService.getCarddeck(tokenusername, uuid);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ExtendedCarddeckListDTO> getCarddecksFromUser(@PathVariable String username,
            @RequestHeader("Authorization") final String token) {
        boolean isAuthorized;
        try {
            isAuthorized = jwtService.checkUsernameCorrespondsWithToken(username, token.substring(7));
        } catch (JWTVerificationException e) {
            isAuthorized = false;
        }

        try {
            ExtendedCarddeckListDTO res = flashcardService.getUserDecks(username, isAuthorized);
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

    @PostMapping("/favorite/add/{uuid}")
    public ResponseEntity<String> addFavorite(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));
            boolean isSuccess = flashcardService.addUserFavorites(tokenusername, uuid);
            if (isSuccess) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/favorite/remove/{uuid}")
    public ResponseEntity<String> removeFavorite(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));
            boolean isSuccess = flashcardService.removeUserFavorites(tokenusername, uuid);
            if (isSuccess) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like/add/{uuid}")
    public ResponseEntity<String> addLike(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));
            boolean isSuccess = flashcardService.addUserLikes(tokenusername, uuid);
            if (isSuccess) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/like/remove/{uuid}")
    public ResponseEntity<String> removeLike(@PathVariable String uuid,
            @RequestHeader("Authorization") final String token) {
        try {
            String tokenusername = jwtService.getUsernameFromToken(token.substring(7));
            boolean isSuccess = flashcardService.removeUserLikes(tokenusername, uuid);
            if (isSuccess) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/{from}/{count}/{orderby}")
    public ResponseEntity<ExtendedCarddeckListDTO> getAll(@PathVariable String from, @PathVariable String count,
            @PathVariable String orderby) {
        try {
            ExtendedCarddeckListDTO res = flashcardService.getAll(Integer.parseInt(from), Integer.parseInt(count),
                    orderby);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userfavorites/{username}")
    public ResponseEntity<ExtendedCarddeckListDTO> getFavoriteCarddecksFromUser(@PathVariable String username,
            @RequestHeader("Authorization") final String token) {
        try {
            boolean authorized = jwtService.checkUsernameCorrespondsWithToken(username, token.substring(7));

            if (authorized) {
                ExtendedCarddeckListDTO res = flashcardService.getFavoriteUserDecks(username);
                if (res != null) {
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }

        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search/{from}/{count}/{orderby}")
    public ResponseEntity<ExtendedCarddeckListDTO> searchCarddecks(@RequestBody CarddeckSearchDTO carddeckSearchDTO,
            @PathVariable String from, @PathVariable String count, @PathVariable String orderby) {

        try {
            ExtendedCarddeckListDTO res = flashcardService.searchCarddecks(carddeckSearchDTO, Integer.parseInt(from),
                    Integer.parseInt(count), orderby);
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
