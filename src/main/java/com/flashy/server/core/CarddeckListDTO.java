package com.flashy.server.core;

import lombok.Data;

import java.util.List;

@Data
public class CarddeckListDTO {
    private String username;
    private List<CarddeckDTO> carddecks;
    private int includesprivate;

    public CarddeckListDTO() {


    }

    public CarddeckListDTO(String username, List<CarddeckDTO> carddecks, int includesprivate) {
        this.username = username;
        this.carddecks = carddecks;
        this.includesprivate = includesprivate;
    }

}
