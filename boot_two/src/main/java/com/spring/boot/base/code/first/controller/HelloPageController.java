package com.spring.boot.base.code.first.controller;

import com.spring.boot.base.BaseController;
import com.spring.boot.base.code.first.service.HelloService;
import com.spring.boot.base.code.two.service.TestUserService;
import com.spring.boot.base.redisConfig.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/hellopage")
public class HelloPageController extends BaseController {
    @Autowired
    private HelloService helloService;
    @Autowired
    private TestUserService testUserService;
    @Autowired
    private RedisConfig redisConfig;

    @RequestMapping("/index")
    public String toIndex(HttpServletRequest reqeust, Model model){
        model.addAttribute("name","世界");
        logger.info("---------日志打印正常---------");
        List<Map<String,Object>> list = helloService.getListUser();//主库数据
        List<Map<String,Object>> listTest = testUserService.getTestUserList();//次库数据
        return "/pages/index";
    }

    @ResponseBody
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
