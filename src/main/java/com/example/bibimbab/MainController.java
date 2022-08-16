package com.example.bibimbab;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    @RequestMapping("/")
    public String root(){
        return "main_page";
    }
    @RequestMapping("/main")
    public String index(){
        return "index";
    }
}
