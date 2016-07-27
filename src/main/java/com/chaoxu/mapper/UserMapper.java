package com.chaoxu.mapper;

import com.chaoxu.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/8.
 */
public interface UserMapper {

    User findUserByusername(String username);

    void updateUser(User user);

    List<User> findAll();

    void saveUser(User user);

    void delUser(Integer id);


    List<User> findByParam(Map<String, Object> params);

    Long count();

    Long countByParam(Map<String, Object> params);

    User findUserById(Integer id);
}
