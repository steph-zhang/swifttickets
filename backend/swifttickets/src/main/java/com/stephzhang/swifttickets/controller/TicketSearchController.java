package com.stephzhang.swifttickets.controller;

import com.stephzhang.swifttickets.dto.LoginFormDTO;
import com.stephzhang.swifttickets.dto.QueryTicketLeftForm;
import com.stephzhang.swifttickets.dto.TicketSearchReq;
import com.stephzhang.swifttickets.service.TicketSearchService;
import com.stephzhang.swifttickets.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.Ticket;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/ticketsearch")
public class TicketSearchController {

    @Resource
    TicketSearchService ticketSearchService;
    @PostMapping("/search")
    public Result search(@RequestBody TicketSearchReq ticketSearchReq, HttpSession session) {
        return ticketSearchService.searchTickets(ticketSearchReq);
    }

    @PostMapping("/queryTicketLeft")
    public Result queryTicketLeft(@RequestBody QueryTicketLeftForm queryTicketLeftForm){
        return ticketSearchService.queryTicketLeft(queryTicketLeftForm);
    }

    @PostMapping("/buyTicket")
    public Result buyTicket(@RequestBody QueryTicketLeftForm queryTicketLeftForm){
        return ticketSearchService.buyTicket(queryTicketLeftForm);
    }

    @GetMapping("/searchAllStations")
    public Result searchAllStations(HttpSession session){
        return ticketSearchService.searchAllStations();
    }
}
