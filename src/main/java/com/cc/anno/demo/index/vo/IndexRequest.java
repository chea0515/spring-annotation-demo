package com.cc.anno.demo.index.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class IndexRequest {
    private String name;
    private String password;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date date;
}
