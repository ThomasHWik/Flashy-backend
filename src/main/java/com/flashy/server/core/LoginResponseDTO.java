package com.flashy.server.core;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String message;
    private int isadmin;
    public LoginResponseDTO() {

    }

    public LoginResponseDTO(String message, int isadmin) {
        this.message = message;
        this.isadmin = isadmin;
    }
}
