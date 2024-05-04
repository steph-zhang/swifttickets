package com.stephzhang.swifttickets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchResp {

    private List<String> trainList;

    private List<Integer> trainBrandList;

    private List<String> departureStationList;

    private List<String> arrivalStationList;
}