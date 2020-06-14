package servletLayerTest;

import com.demo.model.Position;
import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.service.TicketService;
import com.demo.service.TripService;
import com.demo.service.UserService;
import com.demo.servlet.TicketServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TicketService ticketService;

    @Mock
    TripService tripService;

    @Mock
    HttpSession httpSession;

    @Mock
    UserService userService;


    @InjectMocks
    TicketServlet ticketServlet;



    @Test
    public void testDoGetActionTicketDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("ticketDelete");
        when(request.getParameter("ticketId")).thenReturn("1");
        when(ticketService.deleteById(anyInt())).thenReturn(true);

        ticketServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("ticketId");
        verify(response, times(1)).sendRedirect(anyString());
    }
   @Test
    public void testDoGetTicketList() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("ticketList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        ticketServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostSuccess() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("buyTicket");
        when(request.getParameter("tripId")).thenReturn("5");
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        when(tripService.getById(anyInt())).thenReturn(new Trip());
        when(userService.save(any(User.class))).thenReturn(String.valueOf(new User()));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        ticketServlet.doPost(request, response);

        verify(request, times(1)).getParameter("tripId");
        verify(request, times(1)).getSession();
        verify(httpSession, times(1)).getAttribute("user");
        verify(request, times(1)).getRequestDispatcher(anyString());
    }


    @Test
    public void testDoPostUserNull() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("buyTicket");
        when(request.getParameter("tripId")).thenReturn("5");
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(null);
        when(tripService.getById(anyInt())).thenReturn(new Trip());
        when(userService.save(any(User.class))).thenReturn(String.valueOf(new User()));

        ticketServlet.doPost(request, response);
        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("tripId");
        verify(request, times(1)).getSession();
        verify(httpSession, times(1)).getAttribute("user");

    }
}
