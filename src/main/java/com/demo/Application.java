package com.demo;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.impl.TripDaoImpl;
import com.demo.dao.impl.WorkerDaoImpl;
import com.demo.model.Trip;
import com.demo.model.Worker;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        TicketDaoImpl ticketDao = new TicketDaoImpl();

        System.out.println(ticketDao.getTicketListByUserId(2));
    }
}
