package com.flashy.server.core;

import lombok.Data;

@Data
public class FlashcardDTO {
    private String question;
    private String answer;
    private String uuid;

    public FlashcardDTO() {

    }
    public FlashcardDTO(String question, String answer, String uuid) {
        this.question = question;
        this.answer = answer;
        this.uuid = uuid;

    }

}
