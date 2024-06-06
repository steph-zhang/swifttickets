package com.stephzhang.swifttickets.service;

import com.stephzhang.swifttickets.entity.Order;
import com.stephzhang.swifttickets.entity.Route;
import com.stephzhang.swifttickets.entity.User;
import org.springframework.stereotype.Service;

public interface TicketBuyService {


    public void addTicket(Route route);

    public Order generateOrder(Route route, User user);

    public Boolean buyTicket(Route route);
}
