package daoLayerTest;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.interfaces.TicketDao;
import com.demo.exceptions.TicketException;
import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.model.User;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TicketDaoTest {

    TicketDao ticketDao;

    Ticket ticket;

    @Before
    public void setup() {
        ticketDao = new TicketDaoImpl();

        ticket = new Ticket();


        User user = new User();
        user.setId(2);

        Trip trip = new Trip();
        trip.setId(2);

        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setTimeWhenTicketWasBought(new Date());

        ticket = ticketDao.save(ticket);
    }

    @Test
    public void testUpdateMethodDao() {
        ticket.setTimeWhenTicketWasBought(new Date());

        ticketDao.update(ticket);

        Ticket updatedTicket = ticketDao.getById(ticket.getId());

        assertEquals(ticket.getId(), updatedTicket.getId());
    }

    @Test
    public void testGetByIdMethodDao() {
        Ticket getByIdTicket = ticketDao.getById(ticket.getId());

        assertEquals(getByIdTicket.getId(), ticket.getId());
        assertEquals(getByIdTicket.getTrip().getId(), ticket.getTrip().getId());
    }


    @Test
    public void testDeleteByIdMethodDao() {
        List<Ticket> ticketListBeforeDelete = ticketDao.getAll();

        boolean checkIfDelete = ticketDao.deleteById(ticket.getId());

        List<Ticket> ticketListAfterDelete = ticketDao.getAll();

        assertNotEquals(ticketListAfterDelete.size(), ticketListBeforeDelete.size());
        assertTrue(checkIfDelete);
    }

    @Test
    public void testSaveMethodDao() {
        Ticket savedTicket = ticketDao.save(ticket);

        assertEquals(ticket.getId(), savedTicket.getId());
    }

    @Test
    public void testGetTicketListByUserIdMethodDao() {
        List<Ticket> ticketList = ticketDao.getTicketListByUserId(2);

        for(Ticket ticket : ticketList) {
            assertEquals(ticket.getId(), this.ticket.getId());
        }
    }

    @Test(expected = TicketException.class)
    public void testUpdateMethodExceptionDao() {
        ticket.setId(2500);
        ticketDao.update(ticket);
    }

    @Test(expected = TicketException.class)
    public void testGetByIdMethodExceptionDao() {
        ticketDao.getById(2500);
    }

    @Test(expected = TicketException.class)
    public void testDeleteByIdMethodExceptionDao() {
        ticketDao.deleteById(2500);
    }

    @Test(expected = TicketException.class)
    public void testGetTicketListByUserIdMethodExceptionDao() {
        ticketDao.getTicketListByUserId(2500);
    }
}
