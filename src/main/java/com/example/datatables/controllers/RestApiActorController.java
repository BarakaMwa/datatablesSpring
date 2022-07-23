package com.example.datatables.controllers;

import com.example.datatables.models.Actor;
import com.example.datatables.models.Page;
import com.example.datatables.models.PagingRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RestController
@CrossOrigin("http://localhost:8083")
@RequestMapping({"/api"})
public class RestApiDataTablesController {

    @PostMapping("/getActors")
    public Page<Actor> getActors(@RequestBody PagingRequest request){
return
    }

}
