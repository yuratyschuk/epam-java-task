package servletLayerTest;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.service.PositionService;
import com.demo.servlet.PositionServlet;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Mock
    PositionDaoImpl positionDao;

    @InjectMocks
    PositionService positionService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();



    @Test
    public void testDoGetMethodIfActionPositionList()
            throws ServletException, IOException {


        when(request.getParameter("action")).thenReturn("positionList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetMethodIfActionPositionListActive()
            throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("positionListActive");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetMethodIfActionPositionAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
    }

    @Test
    public void testDoGetMethodIfActionPositionDelete() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("positionDelete");
        when(request.getParameter("positionId")).thenReturn("4");
        when(positionDao.deleteById(4)).thenReturn(true);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("positionId");
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/position?action=positionList");
    }

    @Test
    public void testDoGetMethodIfActionPositionUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionUpdate");
        when(request.getParameter("positionId")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("positionId");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void doPostAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("jobName")).thenReturn("test");
        when(request.getParameter("salary")).thenReturn("2000");
        when(request.getParameter("active")).thenReturn("false");

        new PositionServlet().doPost(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("jobName");
        verify(request, atLeast(1)).getParameter("salary");
        verify(request, atLeast(1)).getParameter("active");
    }

    @Test
    public void doPostUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("jobName")).thenReturn("testUpdate");
        when(request.getParameter("salary")).thenReturn("2000");
        when(request.getParameter("active")).thenReturn("false");
        when(request.getParameter("positionId")).thenReturn("3");

        new PositionServlet().doPost(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("jobName");
        verify(request, atLeast(1)).getParameter("salary");
        verify(request, atLeast(1)).getParameter("active");
        verify(request, atLeast(1)).getParameter("positionId");
    }
}
