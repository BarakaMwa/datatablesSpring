package com.example.datatables.contoller.actors;

import com.example.datatables.models.actors.Actor;
import com.example.datatables.models.search.Page;
import com.example.datatables.models.search.PageArray;
import com.example.datatables.models.search.PagingRequest;
import com.example.datatables.services.actors.ActorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequestMapping({"/actor"})
public class ActorController {


    @GetMapping("/dataTable")
    public String actor(@RequestParam(name="name", required=false, defaultValue="Actor Data Tables") String pageName, Model model) {
        log.info(pageName);
        model.addAttribute("pageName", "Actor Data Tables");
        return "datatables";
    }
}
