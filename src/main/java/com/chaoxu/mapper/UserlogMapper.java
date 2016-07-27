package com.chaoxu.mapper;

import com.chaoxu.pojo.UserLog;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/8.
 */
public interface UserlogMapper {

    void saveUserLog(UserLog userlog);

   Long countByParam(Map<String, Object> map);

    List<UserLog> findUserLogByParam(Map<String, Object> param);
}
