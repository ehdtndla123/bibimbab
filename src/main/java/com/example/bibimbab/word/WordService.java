package com.example.bibimbab.word;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class WordService {

    private final WordRepository wordRepository;

    public List<Word> getList(){
        List<Word> wordList=this.wordRepository.findAll();
        return wordList;
    }

    public Page<Word> getLists(int page){
        Pageable pageable=PageRequest.of(page,10);
        return this.wordRepository.findAll(pageable);
    }


    public Word getWord(Integer id){
        Optional<Word> word=this.wordRepository.findById(id);
        if(word.isPresent()){
            return word.get();
        }
        return word.get();
    }

    public String getMeaning(String name){
        Optional<Word> word=this.wordRepository.findByName(name);
        if(word.isPresent()){
            return word.get().getMeaning();
        }
        return word.get().getMeaning();
    }

    public void create(WordForm wordForm){
        Word word= Word.builder()
                .name(wordForm.getName())
                .meaning(wordForm.getMeaning())
                .example(wordForm.getExample())
                .createdDate(LocalDateTime.now())
                .build();
        this.wordRepository.save(word);
    }

}
