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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Token("type-> test")
@RestController
@RequestMapping("/anno/demo/index")
public class IndexController {

    @Autowired
    private IIndexService service;

    @Token("method-> test")
    @RequestMapping(value = "/{name}/{pass}", method = RequestMethod.GET)
    public IndexResponse index(@PathVariable String name, @PathVariable("pass") String password) {
        System.err.println("name: " + name + " , password: " + password);
        return service.indexPage();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public IndexResponse index(@RequestBody IndexRequest request) throws ParseException {
        System.err.println("name: " + request.getName()
                + " , password: " + request.getPassword()
                + ", addTime: " + request.getAddTime());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date addDate = dateFormat.parse(request.getAddTime());
        System.err.println("after ? " + addDate.after(new Date()));
        System.err.println("before ? " + addDate.before(new Date()));
        IndexResponse response = service.indexPage();
        return response;
    }
}
