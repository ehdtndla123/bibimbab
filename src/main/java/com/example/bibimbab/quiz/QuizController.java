package com.example.bibimbab.quiz;
import com.example.bibimbab.user.SiteUser;
import com.example.bibimbab.user.UserDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    /* @RequestMapping("")
    public String quiz_main(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        SiteUser user=new SiteUser("user1",0);

        session.setAttribute("sessionId",user);

        model.addAttribute("username",(SiteUser)session.getAttribute("sessionId"));
        return "quiz_start";
    }
*/
    @GetMapping("")
    public String quiz_start(Model model, @RequestParam(value="quiz_number",defaultValue = "0")int quiz_number,HttpServletRequest request){
        HttpSession session= request.getSession();
        if(session.getAttribute("sessionId")==null){
            return "redirect:/";
        }
        SiteUser user=(SiteUser)session.getAttribute("sessionId");

        List<Quiz> quizList=this.quizService.getQuizList();
        session.setAttribute("quizList",quizList);
        model.addAttribute("quizList",quizList);
        model.addAttribute("quiz_number",quiz_number);

        model.addAttribute("user",user);
        return "quiz_detail";
    }

    @PostMapping("/start")
    public String quiz_start(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();
        return "asd";
    }

    @GetMapping("/submit")
    public String quiz_end(@RequestParam HashMap<String,String> paramMap,HttpServletRequest request){

        HttpSession session= request.getSession();
        List<Quiz> quizList=(List<Quiz>)session.getAttribute("quizList");
        SiteUser user=(SiteUser)session.getAttribute("sessionId");
        int score=0;
        for(int i=0;i<5;i++){
            if(Integer.parseInt(paramMap.get(String.valueOf(i)))==quizList.get(i).getWordList().get(quizList.get(i).getAnswer()).getId()){
                score+=20;
            }
        }
        SiteUser Nuser=new SiteUser(user.getUsername(),score);
        session.removeAttribute("sessionId");
        session.setAttribute("sessionId",Nuser);
        return "redirect:/";
    }

    @RequestMapping("/example")
    public String aqweqwe(){
        return "example";
    }
}
