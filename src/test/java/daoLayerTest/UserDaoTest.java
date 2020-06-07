package daoLayerTest;

import com.demo.dao.impl.UserDaoImpl;
import com.demo.dao.interfaces.UserDao;
import com.demo.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoTest {

    UserDao userDao = new UserDaoImpl();

    User user;

    @Before
        public void setup() {
            user = new User();

            user.setLastName("test user");
            user.setFirstName("test user");
            user.setEmail("test@gmail.com");
            user.setPassword("password");
            user.setUsername("username");

            user = userDao.save(user);
    }

    @Test
    public void testDeleteMethodDao() {
        List<User> userListBeforeDelete = userDao.getAll();

        boolean checkIfDeleted = userDao.delete(user);

        List<User> userListAfterDelete = userDao.getAll();

        assertTrue(checkIfDeleted);
        assertNotEquals(userListAfterDelete.size(), userListBeforeDelete.size());
    }


    @Test
    public void testGetByEmailMethodDao() {
        User getByEmailUser = userDao.getByEmail(user.getEmail());

        assertEquals(getByEmailUser.getEmail(), user.getEmail());
    }

    @Test
    public void testValidateLoginMethodDao() {
        User validateUser = userDao.validateLogin(user.getUsername(), user.getPassword());

        assertEquals(validateUser.getId(), user.getId());
        assertEquals(validateUser.getEmail(), user.getEmail());
        assertEquals(validateUser.getPassword(), user.getPassword());
        assertEquals(validateUser.getUsername(), user.getUsername());
        assertEquals(validateUser.getFirstName(), user.getFirstName());
        assertEquals(validateUser.getLastName(), user.getLastName());
    }
}
