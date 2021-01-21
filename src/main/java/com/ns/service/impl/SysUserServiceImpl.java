package com.ns.service.impl;

import com.ns.dao.Sys_userMapper;
import com.ns.entity.Sys_user;
import com.ns.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl implements ISysUserService{

    @Autowired
    private Sys_userMapper sys_userMapper;

    @Override
    public Sys_user login(String username) {
        return sys_userMapper.queryEmail(username);
    }
}
