package com.stephzhang.swifttickets.service.impl;

import com.stephzhang.swifttickets.entity.Order;
import com.stephzhang.swifttickets.entity.Route;
import com.stephzhang.swifttickets.entity.User;
import com.stephzhang.swifttickets.service.TicketBuyService;
import org.springframework.stereotype.Service;

@Service
public class TicketBuyServiceImpl implements TicketBuyService {
    @Override
    public void addTicket(Route route) {
        // redis
    }

    @Override
    public Order generateOrder(Route route, User user) {
        return null;
    }

    @Override
    public Boolean buyTicket(Route route) {
        return null;
    }

}
