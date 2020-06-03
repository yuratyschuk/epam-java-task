package servletLayerTest;

import com.demo.model.Ticket;
import com.demo.model.User;
import com.demo.service.UserService;
import com.demo.servlet.UserServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    HttpSession session = mock(HttpSession.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    UserService userService = mock(UserService.class);

    User user;
    @Before
    public void setup() {
        user = new User();
        user.setId(1);
        user.setUsername("User");
        user.setPassword("password");
        user.setEmail("test@gmail.com");
        user.setFirstName("Test");
        user.setLastName("Test");
    }

    @Test
    public void testDoGetActionPage() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("page");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn((User) user);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        new UserServlet().doGet(request, response);

        verify(session, atLeastOnce()).getAttribute("user");
        verify(request, atLeastOnce()).setAttribute(anyString(), anyListOf(Ticket.class));
        verify(request, atLeastOnce()).setAttribute(anyString(), any(User.class));
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    public void testDoGetActionUserDelete() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userDelete");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn((User) user);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        new UserServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getSession();
        verify(session, atLeastOnce()).getAttribute("user");
        Assert.assertEquals(session.getAttribute("user"), user);

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoGetActionUserUpdate() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userUpdate");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        new UserServlet().doGet(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getSession();
        verify(session, atLeastOnce()).getAttribute("user");
        Assert.assertEquals(session.getAttribute("user"), user);

        verify(request, atLeastOnce()).getRequestDispatcher(anyString());
        verify(requestDispatcher, atLeastOnce()).forward(request, response);

    }

    @Test
    public void testDoPostActionRegister() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");

        when(request.getParameter("firstName")).thenReturn(user.getFirstName());
        when(request.getParameter("lastName")).thenReturn(user.getLastName());
        when(request.getParameter("email")).thenReturn(user.getEmail());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(request.getParameter("username")).thenReturn(user.getUsername());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        new UserServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getRequestDispatcher(anyString());


    }

    @Test
    public void testDoPostActionLogin() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("testFirstName1");
        when(request.getParameter("username")).thenReturn("testLastName1");
        when(request.getSession()).thenReturn(session);
        
        new UserServlet().doPost(request, response);

        verify(request, atLeastOnce()).getParameter("action");
        verify(request, atLeastOnce()).getSession();
//        verify(session, atLeastOnce()).setAttribute(anyString(), any(User.class));
        verify(response, atLeastOnce()).sendRedirect(request.getContextPath() + "/user?action=page");

    }
}
