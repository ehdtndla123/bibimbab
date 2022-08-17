package com.example.bibimbab.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private int score;

    public UserDTO(String name){
        this.name=name;
        this.score=0;
    }
}
