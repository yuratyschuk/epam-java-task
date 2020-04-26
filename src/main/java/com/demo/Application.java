package com.demo;

import com.demo.model.Ticket;
import com.demo.service.PositionService;
import com.demo.service.TicketService;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        PositionService positionService = new PositionService();
        System.out.println(positionService.getPositionListByActive(true));
    }
}
