package com.ns.controller;

import com.ns.entity.Sys_user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sessionController")
public class SessionController {

    Sys_user sys_user;

    @PostMapping("/getUser")
    @ResponseBody
    public Sys_user getUser(HttpServletRequest request, HttpServletResponse response){
        Object user = request.getSession().getAttribute("user");
        if(user != null){
            sys_user = new Sys_user();
            sys_user = (Sys_user) user;
        }
        return sys_user;
    }

}
