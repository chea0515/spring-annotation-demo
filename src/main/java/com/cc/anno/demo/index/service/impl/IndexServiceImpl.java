package com.cc.anno.demo.index.service.impl;

import com.cc.anno.demo.index.service.IIndexService;
import com.cc.anno.demo.index.vo.IndexResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IndexServiceImpl implements IIndexService {
    @Override
    public IndexResponse indexPage() {
        IndexResponse response = new IndexResponse();
        response.setCode(200);
        response.setMsg("request success. date: " + new Date().getTime());
        return response;
    }
}
