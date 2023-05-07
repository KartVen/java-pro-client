package pl.kartven.javaproapp.ui.model;

public class FieldState {
    private final boolean isActivated;
    private final String value;
    private final boolean isValid;
    private final String error;

    public FieldState(boolean isActivated, String value, boolean isValid, String error) {
        this.isActivated = isActivated;
        this.value = value;
        this.isValid = isValid;
        this.error = error;
    }

    public FieldState(boolean isActivated, String value, boolean isValid) {
        this.isActivated = isActivated;
        this.value = value;
        this.isValid = isValid;
        this.error = null;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public String getValue() {
        return value;
    }

    public String getError() {
        return error;
    }

    public boolean isValid() {
        return isValid;
    }
}