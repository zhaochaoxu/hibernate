package com.chaoxu.service;

import com.chaoxu.mapper.RoleMapper;
import com.chaoxu.pojo.Role;
import com.chaoxu.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by dell on 2016/7/8.
 */
@Named
public class ShiroRealm extends AuthorizingRealm {

    @Inject
    private UserService userService;
    @Inject
    private RoleMapper roleMapper;

    /**
     * 验证是否拥有某种权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       //获取user对象
        User user = (User) principalCollection.getPrimaryPrincipal();
        if(user!= null){
            //根据用户的roleid获取Role对象
            Integer roleId = user.getRoleid();
            Role role = roleMapper.findRoleByroleId(roleId);

            //将用户的值传给info

            //将用户的角色名称赋值给info
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(role.getRolename());
            return info;
        }

        return null;
    }

    /**
     * 验证用户和密码是否正确
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取表单中的账号
        String username = token.getUsername();
        User user = userService.findUserByusername(username);

        if(user!=null){

            if(!user.getEnable()){
                throw new LockedAccountException("账号已被禁用");
            }

            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());

        }else{
            throw new UnknownAccountException("用户名或密码错误");
        }

    }
}
