package com.flashy.server.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Flashyuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String username;

    private String password;

    @NotBlank
    private int isadmin;

    public Flashyuser() {

    }
    public Flashyuser(int id, String username, String password, int isadmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isadmin = isadmin;
    }
    public Flashyuser(String username, String password, int isadmin) {
        this.username = username;
        this.password = password;
        this.isadmin = isadmin;
    }


}
