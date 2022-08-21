package com.example.bibimbab.word;


import com.example.bibimbab.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Page<Word> getLists(int page,String kw){
        List<Sort.Order> list=new ArrayList<>();
        list.add(Sort.Order.desc("voter"));
        Pageable pageable=PageRequest.of(page,20,Sort.by(list));
        Specification<Word> spec=search(kw);
        return this.wordRepository.findAll(spec,pageable);
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
                .voter(0)
                .build();
        this.wordRepository.save(word);
    }

    public void vote(Word word){
       word.setVoter(word.getVoter()+1);
       this.wordRepository.save(word);
    }



    private Specification<Word> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Word> p, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거

                return

                        cb.or(cb.like(p.get("meaning"), "%" + kw + "%"),
                                cb.like(p.get("name"), "%" + kw + "%"),
                                cb.like(p.get("example"), "%" + kw + "%")
                               );

            }
        };
    }
}
