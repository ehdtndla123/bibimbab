package com.example.bibimbab;

import com.example.bibimbab.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MainController {
    @RequestMapping("/")
    public String root(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        SiteUser user=new SiteUser("user1",0);
        session.setAttribute("sessionId",user);

        return "main_page";
    }
    @RequestMapping("/main")
    public String index(){
        return "index";
    }
}
