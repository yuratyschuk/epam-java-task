package servletLayerTest;

import com.demo.model.Worker;
import com.demo.service.PositionService;
import com.demo.service.WorkerService;
import com.demo.servlet.WorkerServlet;
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
public class WorkerServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    WorkerService workerService;

    @Mock
    PositionService positionService;


    @InjectMocks
    WorkerServlet workerServlet;

    @Test
    public void doGetActionDelete() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("workerDelete");
        when(request.getParameter("workerId")).thenReturn("3");
        when(workerService.deleteById(anyInt())).thenReturn(true);

        workerServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("workerId");
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/worker?action=listWorkers");
    }

    @Test
    public void doGetActionUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("workerUpdate");
        when(request.getParameter("workerId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(workerService.getById(anyInt())).thenReturn(new Worker());
        when(positionService.getAll()).thenReturn(anyList());

        workerServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("workerId");
        verify(request, times(1)).setAttribute(anyString(), any(Worker.class));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionAdd() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("workerAdd");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(positionService.getAll()).thenReturn(anyList());

        workerServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionElse() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("workerList");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        workerServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoPostSave() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("workerAdd");
        when(request.getParameter("firstName")).thenReturn("test");
        when(request.getParameter("lastName")).thenReturn("test");
        when(request.getParameter("workingExperience")).thenReturn("2");
        when(request.getParameter("hireDate")).thenReturn("2020-11-20");
        when(request.getParameter("position")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(workerService.save(any(Worker.class))).thenReturn(new Worker());

        workerServlet.doPost(request, response);

        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("workingExperience");
        verify(request, times(1)).getParameter("hireDate");
        verify(request, times(1)).getParameter("position");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("workerUpdate");
        when(request.getParameter("workerId")).thenReturn("2");
        when(request.getParameter("firstName")).thenReturn("test");
        when(request.getParameter("lastName")).thenReturn("test");
        when(request.getParameter("workingExperience")).thenReturn("2");
        when(request.getParameter("hireDate")).thenReturn("2020-11-20");
        when(request.getParameter("position")).thenReturn("2");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(workerService.update(any(Worker.class))).thenReturn(true);

        workerServlet.doPost(request, response);

        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("workingExperience");
        verify(request, times(1)).getParameter("hireDate");
        verify(request, times(1)).getParameter("position");
        verify(request, times(1)).getParameter("workerId");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

}
