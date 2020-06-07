package serviceLayerTest;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.interfaces.TicketDao;
import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    TicketDao ticketDao;

    @InjectMocks
    TicketService ticketService;

    List<Ticket> ticketList;

    Ticket ticket;

    @Before
    public void setup() {
        ticket = new Ticket();
        ticket.setId(1);
        ticket.setTimeWhenTicketWasBought(new Date());
        ticket.setTrip(new Trip());
        ticket.setUser(new User());

        Ticket ticket1 = new Ticket();
        ticket.setId(2);
        ticket.setTimeWhenTicketWasBought(new Date());
        ticket.setTrip(new Trip());
        ticket.setUser(new User());

        ticketList = Arrays.asList(ticket, ticket1);
    }
    
    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        when(ticketDao.save(any(Ticket.class))).thenReturn(new Ticket());
        ticketService.save(new Ticket());

        verify(ticketDao, times(1)).save(any(Ticket.class));
    }


    @Test
    public void testVerifyGetAllMethod() {
        when(ticketDao.getAll()).thenReturn(ticketList);
        List<Ticket> testticketLists = ticketService.getAll();

        verify(ticketDao, times(1)).getAll();
        assertEquals(ticketList.size(), testticketLists.size());
    }

    @Test
    public void testGetByIdMethodCalled() {
        when(ticketDao.getById(anyInt())).thenReturn(ticket);

        Ticket ticketToTest = ticketService.getById(anyInt());

        verify(ticketDao, times(1)).getById(anyInt());
        assertEquals(ticketToTest.getId(), ticket.getId());
    }


    @Test
    public void testDeleteMethodCalled() {
        when(ticketDao.delete(any(Ticket.class))).thenReturn(true);
        boolean testIfDelete = ticketService.delete(new Ticket());

        verify(ticketDao, times(1)).delete(any(Ticket.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {
        when(ticketDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = ticketService.deleteById(1);

        verify(ticketDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }


    @Test
    public void testUpdateMethodCalled() {
        when(ticketDao.update(any(Ticket.class))).thenReturn(true);
        boolean testIfUpdate = ticketService.update(new Ticket());

        verify(ticketDao, times(1)).update(any(Ticket.class));
        assertTrue(testIfUpdate);
    }

    @Test
    public void testGetByUserIdMethodCalled() {
        when(ticketDao.getTicketListByUserId(anyInt())).thenReturn(ticketList);
        List<Ticket> ticketListToTest = ticketService.getTicketListByUserId(1);

        verify(ticketDao, times(1)).getTicketListByUserId(anyInt());
        assertEquals(ticketListToTest.size(), ticketList.size());
    }
}
