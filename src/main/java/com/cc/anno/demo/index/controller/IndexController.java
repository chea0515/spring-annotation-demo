package com.cc.anno.demo.index.controller;

import com.cc.anno.demo.index.service.IIndexService;
import com.cc.anno.demo.index.vo.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
}
