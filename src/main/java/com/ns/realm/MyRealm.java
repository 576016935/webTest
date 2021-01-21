
package com.ns.realm;

import com.ns.entity.Resc;
import com.ns.entity.Role;
import com.ns.entity.Sys_user;
import com.ns.service.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Shiro的数据源
 */

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService sysUserService;
/**
     * 权限控制 - 每次验证权限时都会调用
     * @param principals
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Sys_user user = (Sys_user) principals.getPrimaryPrincipal();

        //从用户对象中直接获得权限列表
        /*List<Resc> rescs = user.getRescs();
        if(rescs != null){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (Resc resc : rescs) {
                if(resc.getRpath() != null && !"".equals(resc.getRpath())) {
                	simpleAuthorizationInfo.addStringPermission(resc.getRpath());
                }
            }
            return simpleAuthorizationInfo;
        }*/
        //获取角色
        List<Role> roles = user.getRoles();
        System.out.println(roles);
        if(roles!= null ){
            SimpleAuthorizationInfo simpleAuthorizationInfo =new SimpleAuthorizationInfo();
            for (Role role :roles){
                simpleAuthorizationInfo.addStringPermission(role.getRescs().get(0).getRpath());
                //simpleAuthorizationInfo.addRole(role.getRolename());
            }
            String rolename = roles.get(0).getRolename();
            simpleAuthorizationInfo.addRole(rolename);
            return simpleAuthorizationInfo;
        }else{
            return null;
        }
    }

    
/**
     * 身份认证
     * @param token
     * @return
     * @throws AuthenticationException
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获得登录时的用户名
        String username = (String) token.getPrincipal();
        Sys_user user = new Sys_user();
        //通过用户名查询用户信息
        user = sysUserService.login(username);
        System.out.println(user);

        if(user != null){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return simpleAuthenticationInfo;
        }else {

        }
        return null;
    }
}

