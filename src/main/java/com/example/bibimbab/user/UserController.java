package com.example.bibimbab.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/change")
    public String changeName(@RequestParam("username")String username, HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession();
        if(session.getAttribute("sessionId")==null){
            SiteUser user=new SiteUser(username,1000);
            session.setAttribute("sessionId",user);
        }else{
            SiteUser user=(SiteUser)session.getAttribute("sessionId");
            session.setAttribute("sessionId",new SiteUser(username,user.getScore()));
        }
        return "redirect:/";
    }
}
