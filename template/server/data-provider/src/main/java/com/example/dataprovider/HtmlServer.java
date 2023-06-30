package com.example.dataprovider;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlServer {
    @GetMapping("/")
    public String root(Model model){
        return "index";
    }
    @GetMapping("/create")
    public String create(Model model) {
        return "index";
    }

    @GetMapping("/retrieve")
    public String retrieve(Model model) {
        return "index";
    }

    @GetMapping("/update")
    public String update(Model model) {
        return "index";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        return "index";
    }
}
