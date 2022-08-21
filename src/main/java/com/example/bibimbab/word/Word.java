package com.example.bibimbab.word;

import com.example.bibimbab.quiz.Quiz;
import com.example.bibimbab.user.SiteUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    @Column(length = 100)
    private String meaning;

    private String example;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne
    private Quiz quiz;

    private int voter;

    @Builder
    public Word(String name,String meaning,String example,LocalDateTime createdDate,LocalDateTime updatedDate,int voter){
        this.name=name;
        this.meaning=meaning;
        this.example=example;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.voter=voter;
    }
    public void setVoter(int voter){
        this.voter=voter;
    }







}
