package com.stephzhang.swifttickets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stephzhang.swifttickets.dto.LoginFormDTO;
import com.stephzhang.swifttickets.entity.Order;
import com.stephzhang.swifttickets.entity.Route;
import com.stephzhang.swifttickets.entity.Ticket;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.utils.Result;

import javax.servlet.http.HttpSession;

public interface UserLoginService extends IService<User> {

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sendCode(String phone, HttpSession session);

    Result info(String phone, HttpSession session);

    Result logout(String token, HttpSession session);

    void generateTicket(Ticket ticket);

    void generateOrder(Order order);
}
