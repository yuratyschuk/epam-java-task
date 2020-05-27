package servletLayerTest;

import com.demo.model.Ticket;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.servlet.TicketServlet;
import com.demo.servlet.TrainServlet;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class TrainServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Test
    public void testDoGetActionDelete() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TrainServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("trainId");
        verify(request, atLeastOnce()).setAttribute(eq("trainList"), anyList());
        verify(response, atLeastOnce()).sendRedirect(anyString());
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionEdit() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("trainId")).thenReturn("3");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);


        new TrainServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("trainId");
        verify(request, atLeastOnce()).setAttribute(eq("train"), any(Train.class));
        verify(request, atLeastOnce()).setAttribute(eq("trainTypeEnum"), eq(TrainType.values()));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetActionInsert() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TrainServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(eq("trainTypeEnum"), eq(TrainType.values()));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetActionTrainList() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TrainServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(eq("trainList"), anyListOf(Train.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoPostSave() throws ServletException, IOException {
        when(request.getParameter("trainName")).thenReturn("test");
        when(request.getParameter("trainNumber")).thenReturn("testNumber");
        when(request.getParameter("maxNumberOfCarriages")).thenReturn("25");
        when(request.getParameter("trainType")).thenReturn(TrainType.CARGO.toString());
        when(request.getParameter("trainId")).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TrainServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("trainName");
        verify(request, atLeastOnce()).getParameter("trainNumber");
        verify(request, atLeastOnce()).getParameter("maxNumberOfCarriages");
        verify(request, atLeastOnce()).getParameter("trainType");
        verify(request, atLeastOnce()).getParameter("trainId");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoPostEdit() throws ServletException, IOException {
        when(request.getParameter("trainName")).thenReturn("testUpdate");
        when(request.getParameter("trainNumber")).thenReturn("testNumberUpdate");
        when(request.getParameter("maxNumberOfCarriages")).thenReturn("24");
        when(request.getParameter("trainType")).thenReturn(TrainType.CARGO.toString());
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new TrainServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("trainName");
        verify(request, atLeastOnce()).getParameter("trainNumber");
        verify(request, atLeastOnce()).getParameter("maxNumberOfCarriages");
        verify(request, atLeastOnce()).getParameter("trainType");
        verify(request, atLeastOnce()).getParameter("trainId");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher,atLeastOnce()).forward(request, response);

    }


}
