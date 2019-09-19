package com.spring.boot.base.code.first.service;

import com.spring.boot.base.code.first.dao.UserMapper;
import com.spring.boot.base.code.first.entity.User;
import com.spring.boot.base.code.first.form.Park;
import com.spring.boot.base.redisConfig.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HelloService implements Serializable {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisServiceImpl redisService;

    @Transactional(transactionManager = "primaryTransactionManager")
    public List<Map<String, Object>> getListUser() {
        System.out.println("-----------------come in----------------");
        userMapper.updateSql("honey");
        //userMapper.updateErrorSql("error");
        testCache("param");
        return userMapper.getListUser();
    }

    public String testCache(String param) {
        userMapper.getListUser();
        Park p1 = new Park();
        Park p2 = new Park();
        Park p3 = new Park();
        p1.setCheer("Bamboo");
        p1.setFlower("red");
        p1.setPool("gray");
        p1.setPool("short");
        p1.setStreet(10);

        p2.setPool("blue");
        p2.setPool("big");
        p2.setStreet(5);

        p3.setCheer("tree");
        p3.setFlower("yellow");

        /*redisService.lSet("park",p1);
        redisService.lSet("park",p2);
        redisService.lSet("park",p3);*/

        //List<Park> list = (List<Park>) redisService.lGet("park",0,2);
        //redisService.set("gg",p1);
        //Map<String,Object> obj =(Map<String,Object>) redisService.get("gg");
        // String str = obj.get("flower").toString();
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUserName("zxf");
        user.setPassword("123456");
        user.setAge("25");
        saveUser(user);
        return "str";
    }


    //!!!!!!!!!!!!!!!!!!!!!注解方式缓存失效，原因未知！！！！！！！！！！！！
    //@CachePut(value = "user", key = "#result.id", unless = "#result eq null")
    //@Cacheable(value = "all", keyGenerator = "keyGenerator")
    public User saveUser(User user) {
        int i = userMapper.saveUser(user);
        return user;
    }


}
