package com.chaoxu.util;

import com.chaoxu.pojo.User;
import org.apache.shiro.SecurityUtils;

import javax.inject.Named;

/**
 * Created by dell on 2016/7/9.
 */
@Named
public class ShiroUtil {

    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static Integer getCurrentUserID() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }

    public static String getCurrentRealName() {
        return getCurrentUser().getRealname();
    }

    public static boolean isAdmin() {
        return getCurrentUser().getRole().getRolename().equals("管理员");
    }

    public static boolean isManager() {
        return getCurrentUser().getRole().getRolename().equals("经理");
    }

    public static boolean isEmployee() {
        return getCurrentUser().getRole().getRolename().equals("员工");
    }

}
