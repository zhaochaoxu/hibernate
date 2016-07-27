package com.chaoxu.controller;

import com.chaoxu.dto.DataTablesResult;
import com.chaoxu.exception.NoFoundException;

import com.chaoxu.pojo.User;
import com.chaoxu.pojo.UserLog;
import com.chaoxu.service.UserService;
import com.chaoxu.util.ShiroUtil;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/7/9.
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Inject
    private UserService userService;

    /**
     * 修改用户密码
     *
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String editPassword() {
        return "setting/password";
    }

    /**
     * 修改用户密码
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public String editPassword(String password) {
        userService.changePassword(password);
        return "success";
    }

    /**
     * 验证原始密码是否正确(Ajax调用)
     *
     * @return
     */
    @RequestMapping(value = "/validate/password", method = RequestMethod.GET)
    @ResponseBody
    public String validateOldPassword(@RequestHeader("X-Requested-With") String xRequestedWith,
                                      String oldpassword) {
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            User user = ShiroUtil.getCurrentUser();
            if (user.getPassword().equals(DigestUtils.md5Hex(oldpassword))) {
                return "true";
            }
            return "false";
        } else {
            throw new NoFoundException();
        }
    }

    /**
     * 显示用户的登录日志
     *
     * @return
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String showUserLog() {

        return "setting/loglist";
    }

    /**
     * 查询当前用户的登录日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "log/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult UserLogLoad(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        List<UserLog> userLogList = userService.findCrrentUserLog(start, length);

        Long count = userService.findCrrentUserLogCount();

        return new DataTablesResult<>(draw, userLogList, count, count);

    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showUsers() {

        //model.addAttribute("userlist",userService.finfAll());
        return "users/userslist";
    }

    /*@RequestMapping(value = "/data.json",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> ShowUsers(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("data",userService.finfAll());
        return map;
    }

    //注意加事务
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(User user) {
        userService.saveUser(user);
        return "success";
    }

    @RequestMapping(value = "/{id:\\\\d+}/delUser",method = RequestMethod.POST)
    @ResponseBody
    public String delUser(@PathVariable Integer id) {
        userService.delUserById(id);
        return "success";
    }
*/
}
