package com.stephzhang.swifttickets.dto;

import lombok.Data;

@Data
public class TicketSearchReq {
    private String FromStation;

    private String ToStation;

    private String departureDate;
}
