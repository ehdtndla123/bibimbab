package com.example.bibimbab.quiz;

import com.example.bibimbab.word.Word;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany (mappedBy = "quiz")
    private List<Word> wordList;

    private int answer;

    @Builder
    public Quiz(List<Word> wordList,int answer){
        this.wordList=wordList;
        this.answer=answer;
    }

}
