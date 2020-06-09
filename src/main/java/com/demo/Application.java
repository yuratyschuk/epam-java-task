package com.demo;

import com.demo.dao.impl.TicketDaoImpl;

public class Application {

    public static void main(String[] args) {
        TicketDaoImpl ticketDao = new TicketDaoImpl();

        System.out.println(ticketDao.getTicketListByUserId(2));
    }
}
