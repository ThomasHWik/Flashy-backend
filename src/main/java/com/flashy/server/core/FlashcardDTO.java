package com.flashy.server.core;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FlashcardDTO {
    private String question;
    private String answer;
    private String uuid;
    private String imagequestion;
    private String imageanswer;

    public FlashcardDTO() {

    }
    public FlashcardDTO(String question, String answer, String uuid) {
        this.question = question;
        this.answer = answer;
        this.uuid = uuid;

    }

    public FlashcardDTO(String question, String answer, String uuid, String imagequestion, String imageanswer) {
        this.question = question;
        this.answer = answer;
        this.uuid = uuid;
        this.imagequestion = imagequestion;
        this.imageanswer = imageanswer;
    }

}
