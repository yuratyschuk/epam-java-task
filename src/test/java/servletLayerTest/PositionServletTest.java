package servletLayerTest;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.dao.interfaces.PositionDao;
import com.demo.model.Position;
import com.demo.service.PositionService;
import com.demo.servlet.PositionServlet;
import javafx.geometry.Pos;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionServletTest {

    @Mock
    HttpServletRequest request = mock(HttpServletRequest.class);
    @Mock
    HttpServletResponse response = mock(HttpServletResponse.class);


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    PositionServlet positionServlet = mock(PositionServlet.class);


    @Test
    public void testDoGetMethodIfActionPositionList()
            throws ServletException, IOException {


        when(request.getParameter("action")).thenReturn("positionList");


        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");

    }

    @Test
    public void testDoGetMethodIfActionPositionListActive()
            throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("positionListActive");
        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
    }

    @Test
    public void testDoGetMethodIfActionPositionAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionAdd");
        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
    }

//    @Test
//    public void testDoGetMethodIfActionPositionDelete() throws ServletException, IOException {
//        PositionService positionService = spy(PositionService.class);
//        PositionDao positionDao = mock(PositionDao.class);
//        when(request.getParameter("action")).thenReturn("positionDelete");
//        when(request.getParameter("positionId")).thenReturn("2");
//        when(positionDao.deleteById(anyInt())).thenReturn(true);
//        positionServlet.doGet(request, response);
//
//        verify(request, atLeast(1)).getParameter("action");
//        verify(request, atLeast(1)).getParameter("positionId");
//    }

    @Test
    public void testDoGetMethodIfActionPositionUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("positionUpdate");
        when(request.getParameter("positionId")).thenReturn("1");

        new PositionServlet().doGet(request, response);

        verify(request, atLeast(1)).getParameter("action");
        verify(request, atLeast(1)).getParameter("positionId");
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
