package serviceLayerTest;

import com.demo.dao.impl.UserDaoImpl;
import com.demo.model.User;
import com.demo.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    User user;

    @Before
    public void setup() {
        user = new User();
        user.setId(1);
    }

    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        when(userDao.save(any(User.class))).thenReturn(new User());
        userService.save(new User());

        verify(userDao, times(1)).save(any(User.class));
    }


    @Test
    public void testDeleteMethodCalled() {
        when(userDao.delete(any(User.class))).thenReturn(true);
        boolean testIfDelete = userService.delete(new User());

        verify(userDao, times(1)).delete(any(User.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testGetByEmailMethodCalled() {
        when(userDao.getByEmail(anyString())).thenReturn(user);

        User userToTest = userService.getByEmail(anyString());

        verify(userDao, times(1)).getByEmail(anyString());
        assertEquals(userToTest.getId(), user.getId());
    }

    @Test
    public void testCheckLoginMethodCalled()  {
        when(userDao.validateLogin(anyString(), anyString())).thenReturn(user);

        User userToTest = userService.checkLogin(anyString(), anyString());

        verify(userDao, times(1)).validateLogin(anyString(), anyString());
        assertEquals(userToTest.getId(), user.getId());
    }



}
