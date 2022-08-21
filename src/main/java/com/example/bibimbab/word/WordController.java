package com.example.bibimbab.word;

import com.example.bibimbab.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    @RequestMapping("")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0")int page,
                       @RequestParam(value="kw",defaultValue = "")String kw)
                       {

        Page<Word> wordList=this.wordService.getLists(page,kw);
        model.addAttribute("kw",kw);
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
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable("id")Integer id){
        Word word=this.wordService.getWord(id);
//        HttpSession session=request.getSession();
//        SiteUser user=new SiteUser("아무개",1000);
//        if(session.getAttribute("sessionId")!=null){
//            user=(SiteUser)session.getAttribute("sessionId");
//        }
        this.wordService.vote(word);

        return String.format("redirect:/word/detail/%s",id);
    }
}
