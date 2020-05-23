package servletLayerTest;

import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.servlet.TicketServlet;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TicketServletTest {

    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    public void testDoGetActionBuyTicket() throws ServletException, IOException {

        Mockito.when(request.getParameter("action")).thenReturn("buyTicket");
        Mockito.when(request.getParameter("tripId")).thenReturn("1");
        Mockito.doNothing().when(request).setAttribute(Mockito.eq("trip"), Mockito.isA(Trip.class));

        new TicketServlet().doGet(request, response);

        Mockito.verify(request, Mockito.atLeast(1)).getParameter("action");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("tripId");
        Mockito.verify(request, Mockito.atLeast(1)).setAttribute(Mockito.eq("trip"),
                Mockito.any(Trip.class));

    }

    @Test
    public void testDoGetActionTicketDelete() throws ServletException, IOException {
        Mockito.when(request.getParameter("action")).thenReturn("ticketDelete");
        Mockito.when(request.getParameter("ticketId")).thenReturn("1");
        Mockito.doNothing().when(response).sendRedirect(Mockito.anyString());

        new TicketServlet().doGet(request, response);

        Mockito.verify(request, Mockito.atLeast(1)).getParameter("action");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("ticketId");
        Mockito.verify(response, Mockito.atLeast(1)).sendRedirect(Mockito.anyString());

    }

    @Test
    public void testDoGetTicketUpdate() throws ServletException, IOException {
        Mockito.when(request.getParameter("action")).thenReturn("ticketUpdate");
        Mockito.when(request.getParameter("ticketId")).thenReturn("7");
        Mockito.doNothing().when(request).setAttribute(Mockito.eq("ticket"), Mockito.isA(Ticket.class));

        new TicketServlet().doGet(request, response);

        Mockito.verify(request, Mockito.atLeast(1)).getParameter("action");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("ticketId");
        Mockito.verify(request, Mockito.atLeast(1)).setAttribute(Mockito.eq("ticket"),
                Mockito.any(Ticket.class));
    }

    @Test
    public void testDoGetTicketList() throws ServletException, IOException {
        Mockito.when(request.getParameter("action")).thenReturn("ticketList");
        Mockito.doNothing().when(request).setAttribute(Mockito.eq("ticketList"), Mockito.anyList());

        new TicketServlet().doGet(request, response);

        Mockito.verify(request, Mockito.atLeast(1)).getParameter("action");
        Mockito.verify(request, Mockito.atLeast(1)).setAttribute(Mockito.eq("ticketList"),
                Mockito.anyList());
    }

    @Test
    public void testDoPost() {
        Mockito.when(request.getParameter("tripId")).thenReturn("1");
        Mockito.when(request.getParameter("firstName")).thenReturn("Test");
        Mockito.when(request.getParameter("lastName")).thenReturn("Test");
        Mockito.when(request.getParameter("email")).thenReturn("test@gmail.com");

        new TicketServlet().doPost(request, response);

        Mockito.verify(request, Mockito.atLeast(1)).getParameter("tripId");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("firstName");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("lastName");
        Mockito.verify(request, Mockito.atLeast(1)).getParameter("email");
    }
}
