package com.example.datatables.apis;

import com.example.datatables.dtos.SelectInputDto;
import com.example.datatables.services.actors.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@Controller
@RequestMapping("/api")
public class SelectRestController {
    private final ActorService actorService;

    @Autowired
    public SelectRestController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping({"/getActorsSelectList"})
    @ResponseBody
    public HashMap<String,Object> getActorsSelectList(HttpServletRequest request) {

        HashMap<String, Object> response = new HashMap<>();
        try{
            response = (HashMap<String, Object>) actorService.getActorsSelectList(request);
            return response;
        }catch(Exception ex){
            response.put("incomplete_results", false);
            response.put("items", null);
            response.put("total_count", 0);
            return response;
        }
    }
}
