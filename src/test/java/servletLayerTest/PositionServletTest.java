package servletLayerTest;

import com.demo.model.Position;
import com.demo.service.PositionService;
import com.demo.servlet.PositionServlet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @InjectMocks
    PositionServlet positionServlet;

    @Mock
    PositionService positionService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();




    @Test
    public void testDoGetMethodIfActionPositionList()
            throws ServletException, IOException {


        when(request.getParameter("action")).thenReturn("positionList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        positionServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetMethodIfActionPositionListActive()
            throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("positionListActive");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        positionServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetMethodIfActionPositionAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        positionServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
    }

    @Test
    public void testDoGetMethodIfActionPositionDelete() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("positionDelete");
        when(request.getParameter("positionId")).thenReturn("4");
        when(positionService.deleteById(anyInt())).thenReturn(true);
      
        positionServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("positionId");
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/position?action=positionList");
    }

    @Test
    public void testDoGetMethodIfActionPositionUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionUpdate");
        when(request.getParameter("positionId")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(positionService.getById(anyInt())).thenReturn(any(Position.class));

        positionServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("positionId");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void doPostAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionAdd");
        when(request.getParameter("jobName")).thenReturn("test");
        when(request.getParameter("salary")).thenReturn("2000");
        when(request.getParameter("active")).thenReturn("false");
        when(positionService.save(any(Position.class))).thenReturn(new Position());

        positionServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("jobName");
        verify(request, times(1)).getParameter("salary");
        verify(request, times(1)).getParameter("active");
        verify(positionService, times(1)).save(any(Position.class));
    }

    @Test
    public void doPostUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionUpdate");
        when(request.getParameter("jobName")).thenReturn("testUpdate");
        when(request.getParameter("salary")).thenReturn("2000");
        when(request.getParameter("active")).thenReturn("false");
        when(request.getParameter("positionId")).thenReturn("3");
        when(positionService.update(any(Position.class))).thenReturn(true);

        positionServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("jobName");
        verify(request, times(1)).getParameter("salary");
        verify(request, times(1)).getParameter("active");
        verify(request, times(1)).getParameter("positionId");
        verify(positionService, times(1)).update(any(Position.class));
    }
}
