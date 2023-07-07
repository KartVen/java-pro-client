package pl.kartven.javaproapp.utils.listener;

import static pl.kartven.javaproapp.utils.utility.FieldUtils.EmailValidator;
import static pl.kartven.javaproapp.utils.utility.FieldUtils.PasswordValidator;

import androidx.lifecycle.LiveData;

public interface LoginEventListener {
    EmailValidator emailValidator = new EmailValidator();
    PasswordValidator passwordValidator = new PasswordValidator();

    default void updateEmailState(String value) {
        emailValidator.setCurrentState(value).validate();
    }

    default void updatePasswordState(String value) {
        passwordValidator.setCurrentState(value).validate();
    }

    default LiveData<Integer> getEmailError() {
        return emailValidator.validate().getErrorState();
    }

    default LiveData<Integer> getPasswordError() {
        return passwordValidator.validate().getErrorState();
    }

    default boolean isEmailFieldActivated() {
        return emailValidator.isActivated();
    }

    default boolean isPasswordFieldActivated() {
        return emailValidator.isActivated();
    }

    default boolean isFieldValidated() {
        return emailValidator.isValidated() && passwordValidator.isValidated();
    }
}
