package com.example.bibimbab;

import com.example.bibimbab.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

@RequiredArgsConstructor
@Controller
public class MainController {
    @RequestMapping("/")
    public String root(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("sessionId")==null){
            SiteUser user = new SiteUser("아무개", 1000);
            session.setAttribute("sessionId", user);
        }
        SiteUser user=(SiteUser)session.getAttribute("sessionId");
        model.addAttribute("user",user);

        return "main_page";

    }
}
