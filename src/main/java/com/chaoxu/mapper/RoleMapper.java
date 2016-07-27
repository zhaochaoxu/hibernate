package com.chaoxu.mapper;

import com.chaoxu.pojo.Role;

import java.util.List;

/**
 * Created by dell on 2016/7/8.
 */
public interface RoleMapper {

    Role findRoleByroleId(Integer id);

    List<Role> findAllRole();

}
