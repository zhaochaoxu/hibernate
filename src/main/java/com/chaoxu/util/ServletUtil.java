package com.chaoxu.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell on 2016/7/9.
 */
public class ServletUtil {
    /**
     * 获取客户端的IP地址
     * @param request
     * @return
     */

    public static String getRemoteIp(HttpServletRequest request){

        String ip = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
