package com.stephzhang.swifttickets.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User {

    /**
     * 用户名
     */
    private String username;

    /**
     * 证件号
     */
    private String id;

    /**
     * 手机号
     */
    private String phone;

}
