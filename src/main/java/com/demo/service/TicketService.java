package com.demo.service;

import com.demo.dao.interfaces.TicketDao;
import com.demo.model.Ticket;

import java.util.List;

public class TicketService {

    private final TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

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

    public Ticket save(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    public List<Ticket> getTicketListByUserId(Integer userId) {
        return ticketDao.getTicketListByUserId(userId);
    }
}
