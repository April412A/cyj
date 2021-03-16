package com.zhuanye.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @Value("${test.hello}")
    private String testhello;
    //http://127.0.0.1:8080/hello
    @GetMapping("/hello")
    public String hello(){
        return "Hello?!"+testhello;
    }

}
