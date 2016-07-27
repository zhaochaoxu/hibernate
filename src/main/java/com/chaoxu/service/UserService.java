package com.chaoxu.service;

import com.chaoxu.mapper.RoleMapper;
import com.chaoxu.mapper.UserMapper;
import com.chaoxu.mapper.UserlogMapper;
import com.chaoxu.pojo.Role;
import com.chaoxu.pojo.User;
import com.chaoxu.pojo.UserLog;
import com.chaoxu.util.ShiroUtil;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/8.
 */
@Named
public class UserService {

    @Inject
    private UserMapper userMapper;
    @Inject
    private RoleMapper roleMapper;
    @Inject
    private UserlogMapper userlogMapper;

    public User findUserByusername(String username) {
        return userMapper.findUserByusername(username);
    }

    /**
     *
     * 创建用户的登录日志
     * @param ip
     */
    public void saveUserLog(String ip) {

        UserLog userLog = new UserLog();
        userLog.setUserid(ShiroUtil.getCurrentUserID());
        userLog.setLoginip(ip);
        userLog.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm"));

        userlogMapper.saveUserLog(userLog);
    }

    /**
     * 修改用户密码
     * @param password
     */
    public void changePassword(String password) {

        User user = ShiroUtil.getCurrentUser();
        user.setPassword(DigestUtils.md5Hex(password));
        userMapper.updateUser(user);
    }

    /**
     * 查询用户的登录数据总数
     * @return
     */
    public Long findCrrentUserLogCount() {

        Map<String,Object> map = Maps.newHashMap();
        map.put("userId", ShiroUtil.getCurrentUserID());

        return userlogMapper.countByParam(map);
    }

    /**
     * 获取当前用户的登录日志
     * @param start
     * @param length
     * @return
     */
    public List<UserLog> findCrrentUserLog(String start, String length) {

        Map<String, Object> param = Maps.newHashMap();
        param.put("start",start);
        param.put("length",length);
        return userlogMapper.findUserLogByParam(param);
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }



    public void delUserById(Integer id) {
        userMapper.delUser(id);
    }

    /**
     * 获取所有的角色
     * @return
     */
    public List<Role> findAllrole() {
        return roleMapper.findAllRole();
    }


    public List<User> findUserListByParam(Map<String, Object> params) {
        return userMapper.findByParam(params);
    }

    /**
     * 获取用户的总数量
     * @return
     */
    public Long findtUserCount() {
        return userMapper.count();
    }


    /**
     * 添加新用户
     * @param user
     */
    @Transactional
    public void saveUser(User user) {
        user.setPassword(DigestUtils.md5Hex("66666"));
        user.setEnable(true);

        // TODO 添加微信平台
        userMapper.saveUser(user);
    }

    /**
     * 根据查询条件获取用户数量
     * @param params
     * @return
     */
    public Long findUserCountByParam(Map<String, Object> params) {
        return userMapper.countByParam(params);
    }

    /**
     * 根据用户的 ID 查询用户
     * @param id
     * @return
     */
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    public void editUser(User user) {

        userMapper.updateUser(user);
    }

    public void resetPassword(Integer id) {
        User user = userMapper.findUserById(id);
        user.setPassword(DigestUtils.md5Hex("666666"));
       userMapper.updateUser(user);
    }

}
