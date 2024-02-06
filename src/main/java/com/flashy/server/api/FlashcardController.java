package com.flashy.server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
