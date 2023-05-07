package pl.kartven.javaproapp.util.validator;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

import pl.kartven.javaproapp.R;

public class RegisterFormValidator extends LoginFormValidator {
    @Inject
    public RegisterFormValidator(Resources resources) {
        super(resources);
    }

    public ValidationResult validateNickname(String value) {
        if (TextUtils.isEmpty(value)) {
            return ValidationResult.error(getString(R.string.error_field_empty));
        } else if (value.length() < 3) {
            return ValidationResult.error(getString(R.string.error_minimum_size));
        } else {
            return ValidationResult.success();
        }
    }

    /*public ValidationResult validateEmail(String value) {
        if (TextUtils.isEmpty(value)) {
            return ValidationResult.error(getString(R.string.error_field_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return ValidationResult.error(getString(R.string.error_bad_format));
        } else {
            return ValidationResult.success();
        }
    }

    public ValidationResult validatePassword(String value) {
        if (TextUtils.isEmpty(value)) {
            return ValidationResult.error(getString(R.string.error_field_empty));
        } else if (value.length() < 8) {
            return ValidationResult.error(String.format(getString(R.string.error_minimum_size), 6));
        } else {
            return ValidationResult.success();
        }
    }*/
}

