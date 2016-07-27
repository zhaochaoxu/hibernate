package com.chaoxu.controller;

import com.chaoxu.dto.FlashMessage;
import com.chaoxu.service.UserService;
import com.chaoxu.util.ServletUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 2016/7/7.
 */
@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Inject
    private UserService userService;

    /**
     * 去登录页面
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(String username, String password,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
        //获取当前登录的用户名
        Subject subject = SecurityUtils.getSubject();
        //判断是否登录在线
        if (subject.isAuthenticated()) {
            //退出登录
            subject.logout();
        }
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            subject.login(usernamePasswordToken);

            //获取当前用户的IP地址

            userService.saveUserLog(ServletUtil.getRemoteIp(request));

            return "redirect:/home";
        } catch (LockedAccountException ex) {
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.STATE_ERROR, "账号已被禁用"));
        } catch (AuthenticationException exception) {
            redirectAttributes.addFlashAttribute("message", new FlashMessage(FlashMessage.STATE_ERROR, "账号或密码错误"));
        }
        return "redirect:/";

    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            redirectAttributes.addFlashAttribute("message",new FlashMessage("安全退出成功！"));
        }

        return "redirect:/";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }


    @RequestMapping("/403")
    public String erro403() {
        return "error/403";
    }
}
