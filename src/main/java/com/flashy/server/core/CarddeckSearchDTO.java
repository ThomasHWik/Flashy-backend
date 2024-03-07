package com.flashy.server.core;

import lombok.Data;

import java.util.List;

@Data
public class CarddeckSearchDTO {
    private String searchquery;

    private List<String> tags;

    public CarddeckSearchDTO() {
    }
}
