package com.example.datatables.contoller.file;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/file")
public class FileController {


    @GetMapping("/index")
    public String files(Model model, @RequestParam(name = "name", defaultValue = "Files", required = false) String pageName) {

        model.addAttribute("pageName", pageName);
        return "createfileandwrite";
    }

}
