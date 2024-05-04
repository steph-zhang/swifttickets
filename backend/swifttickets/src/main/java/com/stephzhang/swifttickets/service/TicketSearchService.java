package com.stephzhang.swifttickets.service;

import com.stephzhang.swifttickets.dto.QueryTicketLeftForm;
import com.stephzhang.swifttickets.dto.TicketSearchReq;
import com.stephzhang.swifttickets.utils.Result;

public interface TicketSearchService {
    Result searchAllStations();

    Result searchTickets(TicketSearchReq ticketSearchReq);

    Result queryTicketLeft(QueryTicketLeftForm queryTicketLeftForm);

    Result buyTicket(QueryTicketLeftForm queryTicketLeftForm);
}
