package com.stephzhang.swifttickets.controller;

import com.stephzhang.swifttickets.dto.LoginFormDTO;
import com.stephzhang.swifttickets.dto.LoginRespDTO;
import com.stephzhang.swifttickets.service.UserLoginService;
import com.stephzhang.swifttickets.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/userlogin")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    @GetMapping("/sendcode")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // 发送短信验证码并保存验证码
        return userLoginService.sendCode(phone, session);
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        System.out.println(loginForm);
/*
        LoginRespDTO loginRespDTO = new LoginRespDTO();
        loginRespDTO.setUsername("admin");
        loginRespDTO.setPhone("12345678900");
        loginRespDTO.setToken("234f2f");
        return Result.ok(loginRespDTO);
*/
        return userLoginService.login(loginForm, session);
    }

    @GetMapping("/info")
    public Result info(@RequestParam String phone, HttpSession session){
        System.out.println(phone);
/*
        LoginRespDTO loginRespDTO = new LoginRespDTO();
        loginRespDTO.setUsername("admin");
        loginRespDTO.setPhone("12345678900");
        loginRespDTO.setToken("234f2f");
        return Result.ok(loginRespDTO);
*/
        return userLoginService.info(phone, session);
    }
}
