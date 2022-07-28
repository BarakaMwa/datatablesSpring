package com.example.datatables.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/select")
public class SelectInputController {

    @GetMapping("/input")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Sever Side Select Input") String pageName, Model model) {
        model.addAttribute("pageName", pageName);
        return "selectInput";
    }
    
}
