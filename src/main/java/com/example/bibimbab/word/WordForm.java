package com.example.bibimbab.word;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class WordForm {
    @NotEmpty(message = "이름은 필수입니다.")
    @Size(max=20,message = "20자 이내입니다.")
    private String name;

    @NotEmpty(message = "뜻은 필수입니다.")
    @Size(max=100,message = "100자 이내입니다.")
    private String meaning;

    @NotEmpty(message = "예시는 필수입니다.")
    @Size(max=400)
    private String example;

    public WordForm(String name,String meaning,String example){
        this.name=name;
        this.meaning=meaning;
        this.example=example;
    }
}
