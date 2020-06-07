package servletLayerTest;

import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.service.TrainService;
import com.demo.servlet.TrainServlet;
import org.junit.Test;
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
public class TrainServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    TrainService trainService;

    @InjectMocks
    TrainServlet trainServlet;


    @Test
    public void testDoGetActionDelete() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("trainDelete");
        when(request.getParameter("trainId")).thenReturn("2");
        when(trainService.deleteById(anyInt())).thenReturn(true);

        trainServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("trainId");
        verify(response, times(1)).sendRedirect(anyString());
    }

    @Test
    public void testDoGetActionUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainUpdate");
        when(request.getParameter("trainId")).thenReturn("3");
        when(trainService.getById(anyInt())).thenReturn(new Train());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        trainServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("trainId");
        verify(request, times(1)).setAttribute(eq("train"), any(Train.class));
        verify(request, times(1)).setAttribute(eq("trainTypeEnum"), eq(TrainType.values()));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionInsert() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        trainServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).setAttribute(eq("trainTypeEnum"), eq(TrainType.values()));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionTrainList() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        trainServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoPostSave() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainAdd");
        when(request.getParameter("trainName")).thenReturn("test");
        when(request.getParameter("trainNumber")).thenReturn("testNumber");
        when(request.getParameter("maxNumberOfCarriages")).thenReturn("25");
        when(request.getParameter("trainType")).thenReturn(TrainType.CARGO.toString());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(trainService.save(any(Train.class))).thenReturn(new Train());

        trainServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("trainName");
        verify(request, times(1)).getParameter("trainNumber");
        verify(request, times(1)).getParameter("maxNumberOfCarriages");
        verify(request, times(1)).getParameter("trainType");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("trainUpdate");
        when(request.getParameter("trainName")).thenReturn("testUpdate");
        when(request.getParameter("trainNumber")).thenReturn("testNumberUpdate");
        when(request.getParameter("maxNumberOfCarriages")).thenReturn("24");
        when(request.getParameter("trainType")).thenReturn(TrainType.CARGO.toString());
        when(request.getParameter("trainId")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(trainService.update(any(Train.class))).thenReturn(true);

        trainServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("trainName");
        verify(request, times(1)).getParameter("trainNumber");
        verify(request, times(1)).getParameter("maxNumberOfCarriages");
        verify(request, times(1)).getParameter("trainType");
        verify(request, times(1)).getParameter("trainId");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }


}
