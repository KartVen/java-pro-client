package pl.kartven.javaproapp.data.model.api.request;

public class LoginApi {
    private String email;
    private String password;

    public LoginApi(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
