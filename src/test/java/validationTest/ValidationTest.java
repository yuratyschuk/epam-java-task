package validationTest;

import com.demo.model.User;
import com.demo.validation.Validation;
import com.demo.validation.ValidationEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationTest {

    User user;

    @Before
    public void setup() {
        user = new User();
        user.setId(1);
        user.setEmail("test@gmail.com");
        user.setPassword("Password1");
        user.setUsername("username");
    }

    @Test
    public void testEmailValidation() {

        ValidationEnum emailValidation = Validation.emailValidation().apply(user);

        assertEquals(emailValidation, ValidationEnum.SUCCESS);

    }

    @Test
    public void testEmailValidationNotSuccessful() {
        user.setEmail("fail");

        ValidationEnum emailValidation = Validation.emailValidation().apply(user);

        assertEquals(emailValidation, ValidationEnum.EMAIL_NOT_VALID);
    }

    @Test
    public void testPasswordValidation() {
        ValidationEnum passwordValidation = Validation.
                passwordValidation().
                and(Validation.passwordSizeValidation()).
                apply(user);

        assertEquals(passwordValidation, ValidationEnum.SUCCESS);
    }

    @Test
    public void testPasswordValidationNotEnoughLength() {
        user.setPassword("Pp1");

        ValidationEnum passwordValidation = Validation.passwordSizeValidation().apply(user);

        assertEquals(passwordValidation, ValidationEnum.PASSWORD_NOT_VALID);
    }

    @Test
    public void testPasswordValidationNotSuccessful() {
        user.setPassword("password1");

        ValidationEnum passwordValidation = Validation.passwordValidation().apply(user);

        assertEquals(passwordValidation, ValidationEnum.PASSWORD_NOT_VALID);
    }

    @Test
    public void testUsernameValidation() {
        ValidationEnum usernameValidation = Validation.userNameValidation().apply(user);

        assertEquals(usernameValidation, ValidationEnum.SUCCESS);
    }

    @Test
    public void testUsernameValidationNotSuccessful() {
        user.setUsername("e");
        ValidationEnum usernameValidation = Validation.userNameValidation().apply(user);

        assertEquals(usernameValidation, ValidationEnum.USERNAME_NOT_VALID);
    }

    @Test
    public void testFullUserValidation() {
        ValidationEnum fullValidation = Validation.emailValidation().
                and(Validation.passwordSizeValidation()).
                and(Validation.passwordValidation()).
                apply(user);

    assertEquals(fullValidation, ValidationEnum.SUCCESS);
    }
}
