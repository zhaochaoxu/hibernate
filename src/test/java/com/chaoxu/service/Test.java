package com.chaoxu.service;

import com.chaoxu.pojo.User;

import com.chaoxu.util.ShiroUtil;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.apache.shiro.SecurityUtils;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 2016/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class Test {


    @Inject
    private UserService userService;
    @Inject
    private ShiroUtil shiroUtil;
    @org.junit.Test
    public void testFindAllUser(){

        List<User> user = userService.findAll();

        Assert.assertNotNull(user);

        System.out.println("查询所有用户》》》》》》{}"+user);

    }
   /* @org.junit.Test
    public  void tesShiroBoolean(){
        System.out.println("测试的结果+》》》》"+ShiroUtil.getCrrentUserName()+">>>>>>>>>>>>>..");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<"+ShiroUtil.getCurrentUser()+">>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<"+ShiroUtil.isAdmin()+">>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<"+ShiroUtil.isEmployee()+">>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<"+ShiroUtil.isManager()+">>>>>>>>>>>>>>>>>>>>>>>>");
    }*/
}
