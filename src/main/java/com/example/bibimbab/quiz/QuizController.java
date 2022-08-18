package com.example.bibimbab.quiz;
import com.example.bibimbab.user.SiteUser;
import com.example.bibimbab.user.UserDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
}
