package com.example.datatables.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RestController
@CrossOrigin("http://localhost:8083")
@RequestMapping({"/","/index"})
public class ActorRestController {
}
