package com.example.datatables.apis;

import com.example.datatables.models.actors.Actor;
import com.example.datatables.models.search.Page;
import com.example.datatables.models.search.PageArray;
import com.example.datatables.models.search.PagingRequest;
import com.example.datatables.services.actors.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api"})
public class ActorRestController {


    private final ActorService actorService;

    @Autowired
    public ActorRestController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping({"/getActors"})
    @ResponseBody
    public Page<Actor> list(@RequestBody PagingRequest request) {
        return actorService.getActors(request);
    }

    @PostMapping("/array")
    @ResponseBody
    public PageArray array(@RequestBody PagingRequest request) {
        return actorService.getActorsArray(request);
    }


}
