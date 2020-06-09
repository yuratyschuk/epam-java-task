package com.demo.validation;

import com.demo.model.User;

import java.util.function.Function;

import static com.demo.validation.ValidationEnum.*;

public interface Validation extends Function<User, ValidationEnum> {

    static Validation passwordValidation() {
        String patternForStringValidation = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        return user -> user.getPassword().matches(patternForStringValidation) ?
                SUCCESS : PASSWORD_NOT_VALID;
    }

    static Validation passwordSizeValidation() {
        return user -> user.getPassword().length() >= 8 ?
                SUCCESS : PASSWORD_NOT_VALID;
    }

    static Validation userNameValidation() {
        return user -> user.getUsername().length() > 6 ?
                SUCCESS : USERNAME_NOT_VALID;
    }

    static Validation emailValidation() {
        String patternForEmailValidation = "^.+@.+\\..+$";
        return user -> user.getEmail().matches(patternForEmailValidation) ?
                SUCCESS : EMAIL_NOT_VALID;
    }

    default Validation and(Validation other) {
        return user -> {
            ValidationEnum result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }

}
