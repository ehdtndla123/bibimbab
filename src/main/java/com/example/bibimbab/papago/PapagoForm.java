package com.example.bibimbab.papago;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PapagoForm {

    @NotEmpty(message = "번역할 내용은 필수입니다.")
    @Size(max=1000)
    private String translation;

}
