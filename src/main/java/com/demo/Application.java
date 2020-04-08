package com.demo;

import com.demo.dao.impl.*;
import com.demo.model.*;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        TicketDaoImpl ticketDao = new TicketDaoImpl();
        Ticket ticket = ticketDao.findById(2);
        System.out.println(ticket);
    }
}
