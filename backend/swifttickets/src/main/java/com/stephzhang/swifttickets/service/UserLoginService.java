package com.stephzhang.swifttickets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stephzhang.swifttickets.dto.LoginFormDTO;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.utils.Result;

import javax.servlet.http.HttpSession;

public interface UserLoginService extends IService<User> {

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sendCode(String phone, HttpSession session);

    Result info(String phone, HttpSession session);
}
