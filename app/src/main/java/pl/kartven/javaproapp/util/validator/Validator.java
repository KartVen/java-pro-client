package pl.kartven.javaproapp.util.validator;

import android.content.res.Resources;

import javax.inject.Inject;

public abstract class Validator {
    protected Resources resources;

    public Validator(Resources resources) {
        this.resources = resources;
    }

    protected String getString(int stringResId) {
        return resources.getString(stringResId);
    }

    public static class ValidationResult {
        private final boolean isSuccess;
        private final String errorMessage;

        private ValidationResult(boolean isSuccess, String errorMessage) {
            this.isSuccess = isSuccess;
            this.errorMessage = errorMessage;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult error(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
