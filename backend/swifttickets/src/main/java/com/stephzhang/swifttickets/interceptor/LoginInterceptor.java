package com.stephzhang.swifttickets.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.utils.UserHolder;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Data
public class LoginInterceptor implements HandlerInterceptor {

    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token == null){
            response.setStatus(401);
            return false;
        }
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(token);
        User user = BeanUtil.fillBeanWithMap(entries, new User(), false);
        UserHolder.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
