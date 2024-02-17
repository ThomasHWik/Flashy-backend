package com.flashy.server.core;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private int isadmin;

    public UserDTO() {}
    public UserDTO(String username, String password, int isadmin) {

    }
}
