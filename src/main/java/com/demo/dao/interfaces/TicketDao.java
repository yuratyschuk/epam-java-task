package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Ticket;

import java.util.List;

public interface TicketDao extends DAO<Ticket> {


    List<Ticket> getTicketListByUserId(Integer userId);
}
