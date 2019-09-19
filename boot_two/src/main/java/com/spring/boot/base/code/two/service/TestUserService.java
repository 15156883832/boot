package com.spring.boot.base.code.two.service;

import com.spring.boot.base.code.two.dao.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestUserService {
    @Autowired
    TestUserMapper testUserMapper;

    public List<Map<String,Object>> getTestUserList(){

        return testUserMapper.getTestUserList();
    }

}
