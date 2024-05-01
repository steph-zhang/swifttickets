package com.stephzhang.swifttickets.dto;

import lombok.Data;

@Data
public class LoginRespDTO {
    private String phone;
    private String username;
    private String token;
}
