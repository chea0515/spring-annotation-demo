package com.cc.anno.demo.index.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anno/demo/home")
public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String homePage() {
        return "/home";
    }

}
