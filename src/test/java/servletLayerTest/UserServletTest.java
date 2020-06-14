package servletLayerTest;

import com.demo.model.Ticket;
import com.demo.model.User;
import com.demo.service.TicketService;
import com.demo.service.UserService;
import com.demo.servlet.UserServlet;
import com.demo.validation.ValidationEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;


    @Mock
    HttpSession session;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    UserService userService;

    @Mock
    TicketService ticketService;

    @InjectMocks
    UserServlet userServlet;

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

        when(request.getParameter("action")).thenReturn("userPage");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(session, times(1)).getAttribute("user");
        verify(request, times(1)).setAttribute(anyString(), anyListOf(Ticket.class));
        verify(request, times(1)).setAttribute(anyString(), any(User.class));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetActionUserDelete() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userDelete");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        when(userService.delete(any(User.class))).thenReturn(true);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute("user");

        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionUserUpdate() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userUpdate");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute("user");

        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }

    @Test
    public void testDoGetActionUserExit() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userExit");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(session, times(1)).setAttribute(anyString(), eq(null));
        verify(requestDispatcher,times(1)).forward(request, response);
    }

    @Test
    public void testDoPostActionRegister() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userRegister");
        when(request.getParameter("firstName")).thenReturn(user.getFirstName());
        when(request.getParameter("lastName")).thenReturn(user.getLastName());
        when(request.getParameter("email")).thenReturn(user.getEmail());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(request.getParameter("username")).thenReturn(user.getUsername());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(userService.save(any(User.class))).thenReturn(ValidationEnum.SUCCESS.toString());

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getRequestDispatcher(anyString());


    }

    @Test
    public void testDoPostActionLogin() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userLogin");
        when(request.getParameter("password")).thenReturn("testFirstName1");
        when(request.getParameter("username")).thenReturn("testLastName1");
        when(request.getSession()).thenReturn(session);
        when(userService.checkLogin(anyString(), anyString())).thenReturn(new User());

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getSession();
        verify(session, times(1)).setAttribute(anyString(), any(User.class));

    }

    @Test
    public void testDoPostChangePassword() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userChangePassword");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(request.getParameter("password")).thenReturn("passworD1");
        when(request.getParameter("repeatedPassword")).thenReturn("passworD1");
        when(userService.changePassword(any(User.class), anyString())).thenReturn(true);

        userServlet.doPost(request, response);

        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getParameter("repeatedPassword");
        verify(userService, times(1)).changePassword(any(User.class), anyString());
        verify(response, times(1)).sendRedirect(anyString());
    }

    @Test
    public void testDoPostChangePasswordPasswordNotValid() throws ServletException, IOException {

        when(request.getParameter("action")).thenReturn("userChangePassword");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(request.getParameter("password")).thenReturn("passworD1");
        when(request.getParameter("repeatedPassword")).thenReturn("passworD1");
        when(userService.changePassword(any(User.class), anyString())).thenReturn(false);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        userServlet.doPost(request, response);

        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getParameter("repeatedPassword");
        verify(userService, times(1)).changePassword(any(User.class), anyString());
        verify(request, times(1)).setAttribute(anyString(), anyString());
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPostChangeUserInfoValidationNotSuccessful() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userUpdateInfo");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("username")).thenReturn("changedUsername");
        when(request.getParameter("firstName")).thenReturn("changedFirstName");
        when(request.getParameter("lastName")).thenReturn("changedLastName");
        when(request.getParameter("email")).thenReturn("changed_email@gmail.com");
        when(userService.update(any(User.class))).thenReturn("validationNotSuccessful");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute("user");
        verify(request, times(1)).getParameter("username");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).setAttribute(anyString(), any(String.class));
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }


    @Test
    public void testDoPostChangeUserInfoValidationSuccessful() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userUpdateInfo");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("username")).thenReturn("changedUsername");
        when(request.getParameter("firstName")).thenReturn("changedFirstName");
        when(request.getParameter("lastName")).thenReturn("changedLastName");
        when(request.getParameter("email")).thenReturn("changed_email@gmail.com");
        when(userService.update(any(User.class))).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute("user");
        verify(request, times(1)).getParameter("username");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("email");
        verify(session, times(1)).setAttribute(anyString(), any(User.class));

        verify(response, times(1)).sendRedirect(anyString());

    }

    @Test
    public void testDoPostRegisterUserNotValid() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userRegister");
        when(request.getParameter("username")).thenReturn("username");
        when(request.getParameter("firstName")).thenReturn("firstName");
        when(request.getParameter("lastName")).thenReturn("lastName");
        when(request.getParameter("email")).thenReturn("email@gmail.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.save(any(User.class))).thenReturn("notValid");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("username");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).getParameter("password");

        verify(request, times(1)).setAttribute(anyString(), anyString());
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }


    @Test
    public void testDoPostRegisterUserValid() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userRegister");
        when(request.getParameter("firstName")).thenReturn("firstName");
        when(request.getParameter("lastName")).thenReturn("lastName");
        when(request.getParameter("email")).thenReturn("email@gmail.com");
        when(request.getParameter("username")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.save(any(User.class))).thenReturn("");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("action");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("email");
        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getParameter("username");
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testLoginUserNotValid() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("userLogin");
        when(request.getParameter("username")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("password");
        when(userService.checkLogin(anyString(), anyString())).thenReturn(null);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).getParameter("password");
        verify(request, times(1)).getParameter("username");
        verify(request, times(1)).setAttribute(anyString(), anyString());
        verify(request, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(request, response);

    }
}
