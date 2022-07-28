package com.example.datatables.contoller;

import com.example.datatables.models.Actor;
import com.example.datatables.models.Page;
import com.example.datatables.models.PageArray;
import com.example.datatables.models.PagingRequest;
import com.example.datatables.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
