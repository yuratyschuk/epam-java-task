package servletLayerTest;

import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.servlet.WorkerServlet;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.*;
public class WorkerServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);


    @Test
    public void doGetActionDelete() throws IOException, ServletException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("workerId")).thenReturn("3");

        new WorkerServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("workerId");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Worker.class));
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/worker?action=listWorkers");
    }

    @Test
    public void doGetActionEdit() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("workerId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);


        new WorkerServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getParameter("workerId");
        verify(request, atLeastOnce()).setAttribute(anyString(), any(Worker.class));
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Position.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetActionInsert() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new WorkerServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Position.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetActionElse() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("else");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new WorkerServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn("test");
        when(request.getParameter("lastName")).thenReturn("test");
        when(request.getParameter("workingExperience")).thenReturn("2");
        when(request.getParameter("hireDate")).thenReturn("2020-11-20");
        when(request.getParameter("position")).thenReturn("2");
        when(request.getParameter("workerId")).thenReturn("4");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new WorkerServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("firstName");
        verify(request, atLeastOnce()).getParameter("lastName");
        verify(request, atLeastOnce()).getParameter("workingExperience");
        verify(request, atLeastOnce()).getParameter("hireDate");
        verify(request, atLeastOnce()).getParameter("position");
        verify(request, atLeastOnce()).getParameter("workerId");
        verify(request,atLeastOnce()).setAttribute(anyString(), anyListOf(Worker.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

}
