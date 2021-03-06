package com.cyj.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    //http://127.0.0.1:8080/hello
    @RequestMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
