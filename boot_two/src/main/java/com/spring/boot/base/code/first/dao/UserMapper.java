package com.spring.boot.base.code.first.dao;

import com.spring.boot.base.code.first.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<Map<String,Object>> getListUser();

    int updateSql(String name);

    int updateErrorSql(String name);

    int saveUser(User user);

}
