package com.ns.controller;

import com.ns.entity.Role;
import com.ns.entity.Sys_user;
import com.ns.service.ISysUserService;
import com.ns.util.CodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("/login")
    //@ResponseBody
    public String login(HttpServletRequest request){
        Map<String,Object> map  = new HashMap<>();
        Sys_user sys_user = new Sys_user();
        HttpSession userSession = request.getSession();
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            try {
                subject.login(token);
                // 剔除其他此账号在其它地方登录
                List<Session> loginedList = getLoginedSession(subject);
                for (Session session : loginedList) {
                    session.stop();
                }
                //获取存入shiro的用户对象信息并存入session
                sys_user = (Sys_user)subject.getPrincipal();
                //sys_user.setRoles(((Sys_user) subject.getPrincipal()).getRoles());
                userSession.setAttribute("user",sys_user);
                request.setAttribute("user",sys_user);
                return "redirect:../index.jsp";
            }catch (UnknownAccountException e){
                System.out.println("账号错误");
                request.setAttribute("msg",CodeUtil.LOGIN_ERROR);
                return "redirect:../login.jsp";
            }catch (IncorrectCredentialsException e){
                //密码错误
                System.out.println("密码错误");
                request.setAttribute("msg",CodeUtil.LOGIN_ERROR);
                return "redirect:../login.jsp";
            }catch (AuthenticationException e){
                //账号错误
                System.out.println("账号不存在");
                request.setAttribute("msg",CodeUtil.LOGIN_ERROR);
                return "redirect:../login.jsp";
            }
        }
        return "";
        /*if(Sys_user!=null){
            if(username.equals(Sys_user.getAccount()) && password.equals(Sys_user.getPassword())){
                request.getSession().setAttribute("username",Sys_user.getAccount());
                map.put("WEBJK","A-0000");
                return "redirect:../index.jsp";
            }else {
                map.put("WEBJK","A-0001");
                return "redirect:../login.jsp";
            }
        }else{
            map.put("WEBJK","A-0001");
            return "redirect:../login.jsp";
        }*/
    }

    private List<Session> getLoginedSession(Subject subject) {
        Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
                .getSecurityManager()).getSessionManager()).getSessionDAO()
                .getActiveSessions();
        List<Session> loginedList = new ArrayList<Session>();
        Sys_user loginUser = (Sys_user) subject.getPrincipal();
        for (Session session : list) {
            Subject s = new Subject.Builder().session(session).buildSubject();
            if (s.isAuthenticated()) {
                Sys_user user = (Sys_user) s.getPrincipal();
                if (user.getId()==(loginUser.getId())) {
                    if (!session.getId().equals(subject.getSession().getId())) {
                        loginedList.add(session);
                    }
                }
            }
        }
        return loginedList;
    }
}
