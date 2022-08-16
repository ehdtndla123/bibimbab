package com.example.bibimbab.word;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10,unique = true)
    private String name;

    @Column(length = 100)
    private String meaning;

    private String example;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Builder
    public Word(String name,String meaning,String example,LocalDateTime createdDate,LocalDateTime updatedDate){
        this.name=name;
        this.meaning=meaning;
        this.example=example;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
    }







}
