package com.stephzhang.swifttickets.dto;

import lombok.Data;

@Data
public class QueryTicketLeftForm {
    private String path;
    private String trainname;
    private String fromStation;
    private String toStation;
    private String departureDate;
}
