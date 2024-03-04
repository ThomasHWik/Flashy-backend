package com.flashy.server.core;

import com.flashy.server.data.dataviews.Extendedcarddeckview;
import lombok.Data;

import java.util.List;

@Data
public class ExtendedCarddeckListDTO {

    private List<ExtendedCarddeckDTO> carddecks;
    private String username;
    private int includesprivate;
    private int count;
    private int start;

    public ExtendedCarddeckListDTO() {


    }

    public ExtendedCarddeckListDTO(String username, List<ExtendedCarddeckDTO> carddecks, int includesprivate, int count, int start) {
        this.username = username;
        this.carddecks = carddecks;
        this.includesprivate = includesprivate;
        this.count = count;
        this.start = start;
    }

}
