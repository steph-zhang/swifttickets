package com.stephzhang.swifttickets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.stephzhang.swifttickets.dto.LoginFormDTO;
import com.stephzhang.swifttickets.dto.LoginRespDTO;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.mapper.UserMapper;
import com.stephzhang.swifttickets.service.UserLoginService;
import com.stephzhang.swifttickets.utils.RegexUtils;
import com.stephzhang.swifttickets.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
@Slf4j
public class UserLoginServiceImpl extends ServiceImpl<UserMapper, User> implements UserLoginService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        String phone = loginForm.getPhone();
        System.out.println(phone+"login");
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("手机号格式错误");
        }
        String cacheCode = stringRedisTemplate.opsForValue().get("login:code:" + phone);
        String code = loginForm.getCode();

        if(cacheCode == null || !cacheCode.equals(code)){
            return Result.fail("验证码错误");
        }

        User user = query().eq("phone", phone).one();

        if(user == null){
            user = createUserWithPhone(phone);
        }
        String token = UUID.randomUUID().toString(true);
        User userDTO = BeanUtil.copyProperties(user, User.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll("login:token:" + token, userMap);
        stringRedisTemplate.expire("login:token:" + token, 1, TimeUnit.HOURS);
        LoginRespDTO loginRespDTO = new LoginRespDTO();
        loginRespDTO.setUsername(user.getUsername());
        loginRespDTO.setPhone(user.getPhone());
        loginRespDTO.setToken(token);
        return Result.ok(loginRespDTO);
    }

    @Override
    public Result sendCode(String phone, HttpSession session) {
        System.out.println(phone);
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式错误");
        }
        String code = RandomUtil.randomNumbers(6);

        stringRedisTemplate.opsForValue().set("login:code:" + phone, code, 5, TimeUnit.MINUTES);

        log.debug("验证码：{}", code);

        return Result.ok();
    }

    @Override
    public Result info(String phone, HttpSession session) {
        User user = query().eq("phone", phone).one();
        return Result.ok(user);
    }

    @Override
    public Result logout(String token, HttpSession session) {
        System.out.println(token);
        return Result.ok();
    }

    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setUsername("username_" + phone);
        user.setId("id_" + phone);
        save(user);
        return user;
    }
}
