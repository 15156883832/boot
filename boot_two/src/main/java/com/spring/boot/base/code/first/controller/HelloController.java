package com.spring.boot.base.code.first.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value="helloWorld")
    public String helloWorld(HttpServletRequest request){
        System.out.println("Hello World!");
        return "Hello World!";
    }

    @RequestMapping(value="listMap")
    public List<Map<String,Object>> listMap(HttpServletRequest request){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        map.put("id","0");
        map1.put("id","1");
        map2.put("id","2");
        map.put("name","tony");
        map1.put("name","nick");
        map2.put("name",new Date());
        list.add(map);
        list.add(map1);
        list.add(map2);
        System.out.println("Hello World!");
        return list;
    }

}
