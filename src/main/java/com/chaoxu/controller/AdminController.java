package com.chaoxu.controller;

import com.chaoxu.dto.DataTablesResult;
import com.chaoxu.dto.JSONresult;

import com.chaoxu.pojo.User;
import com.chaoxu.service.UserService;
import com.chaoxu.util.Strings;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/11.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Inject
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showUsers(Model model) {

        model.addAttribute("rolelist", userService.findAllrole());

        return "users/userslist";
    }


    @RequestMapping(value = "/users/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<User> loadUsers(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUTF8(keyword);

        Map<String, Object> params = Maps.newHashMap();
        params.put("keyword", keyword);
        params.put("start", start);
        params.put("length", length);

        List<User> userList = userService.findUserListByParam(params);
        Long count = userService.findtUserCount();
        Long filterCount = userService.findUserCountByParam(params);

        return new DataTablesResult<>(draw, userList, count, filterCount);
    }


    /**
     * 添加新用户
     *
     * @return
     */
    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(User user) {
        userService.saveUser(user);
        return "success";
    }

    /**
     * 检测账号是否被占用
     *
     * @param username 账号
     * @return
     */
    @RequestMapping(value = "users/checkusername", method = RequestMethod.GET)
    @ResponseBody
    public String Checkusename(String username) {

        User user = userService.findUserByusername(username);
        if (user == null) {
            return "true";

        } else {
            return "false";
        }

    }

    /**
     * 根据用户的ID查询用户
     *
     * @return
     */
    @RequestMapping(value = "/users/{id:\\d+}.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONresult editUser(@PathVariable Integer id) {
        User user = userService.findUserById(id);

        if (user == null) {
            return new JSONresult("找不到对应" + id + "的用户");
        } else {
            return new JSONresult(user);
        }
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editUser(User user) {
        userService.editUser(user);
        return "success";
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/resetpassword",method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(Integer id){
       userService.resetPassword(id);
        return "success";

    }
}
