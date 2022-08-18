package com.example.bibimbab.word;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    @RequestMapping("")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0")int page)
                       {

        Page<Word> wordList=this.wordService.getLists(page);

        model.addAttribute("wordList",wordList);
        return "wordList";
    }

    @GetMapping("/create")
    public String create(Model model, WordForm wordForm){
        return "word_create_form";
    }
    @PostMapping("/create")
    public String create(@Valid WordForm wordForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "word_create_form";
        }
        System.out.println(wordForm.getName());
        this.wordService.create(wordForm);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id")Integer id){
        Word word=this.wordService.getWord(id);
        model.addAttribute("word",word);
        return "word_detail";
    }

}
