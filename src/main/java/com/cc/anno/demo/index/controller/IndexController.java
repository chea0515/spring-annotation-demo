package com.cc.anno.demo.index.controller;

import com.cc.anno.demo.index.vo.IndexResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/anno/demo/index")
public class IndexController {

    @RequestMapping(value = "/{name}/{pass}", method = RequestMethod.GET)
    public IndexResponse index(@PathVariable String name, @PathVariable("pass") String password) {
        System.err.println("name: " + name + " , password: " + password);
        IndexResponse response = new IndexResponse();
        response.setCode(200);
        response.setMsg("request success. date: " + new Date().getTime());
        return response;
    }
}
