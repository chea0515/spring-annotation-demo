package com.cc.anno.demo.index.controller;

import com.cc.anno.demo.config.Token;
import com.cc.anno.demo.index.service.IIndexService;
import com.cc.anno.demo.index.vo.IndexRequest;
import com.cc.anno.demo.index.vo.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anno/demo/index")
public class IndexController {

    @Autowired
    private IIndexService service;

    @RequestMapping(value = "/{name}/{pass}", method = RequestMethod.GET)
    public IndexResponse index(@PathVariable String name, @PathVariable("pass") String password) {
        System.err.println("name: " + name + " , password: " + password);
        return service.indexPage();
    }

    @Token("now")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public IndexResponse index(@RequestBody IndexRequest request) {
        System.err.println("name: " + request.getName()
                + " , password: " + request.getPassword()
                + ", date: " + request.getDate());
        IndexResponse response = service.indexPage();
        return response;
    }
}
