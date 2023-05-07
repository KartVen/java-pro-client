package pl.kartven.javaproapp.data.model;

public class AuthLoginRequest {
    private String email;
    private String password;

    public AuthLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
