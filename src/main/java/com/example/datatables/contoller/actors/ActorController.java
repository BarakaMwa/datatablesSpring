package com.example.datatables.contoller.actors;

import com.example.datatables.models.actors.Actor;
import com.example.datatables.models.search.Page;
import com.example.datatables.models.search.PageArray;
import com.example.datatables.models.search.PagingRequest;
import com.example.datatables.services.actors.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/actor"})
public class ActorController {


    @GetMapping("/dataTable")
    public String actor(@RequestParam(name="name", required=false, defaultValue="Actor Data Tables") String pageName, Model model) {
        model.addAttribute("pageName", pageName);
        return "datatables";
    }
}
