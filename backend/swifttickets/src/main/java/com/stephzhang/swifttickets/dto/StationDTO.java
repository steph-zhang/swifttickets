package com.stephzhang.swifttickets.dto;

import lombok.Data;

@Data
public class StationDTO {
    private String name;
    private String code;

    public StationDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
