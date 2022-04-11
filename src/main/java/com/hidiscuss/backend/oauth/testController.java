package com.hidiscuss.backend.oauth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/test")
    public String index() {
        return "Hello World";
    }
}
