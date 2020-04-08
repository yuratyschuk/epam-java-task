package com.demo.service;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.model.Ticket;

import java.util.List;

public class TicketService {

    TicketDaoImpl ticketDao = new TicketDaoImpl();

    public boolean update(Ticket ticket) {
        return ticketDao.update(ticket);
    }

    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    public Ticket getById(int id) {
        return ticketDao.getById(id);
    }

    public boolean delete(Ticket ticket) {
        return ticketDao.delete(ticket);
    }

    public boolean deleteById(int id) {
        return ticketDao.deleteById(id);
    }

    public boolean save(Ticket ticket) {
        return ticketDao.save(ticket);
    }
}
