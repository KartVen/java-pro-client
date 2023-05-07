package pl.kartven.javaproapp.data.model;

public class AuthRegisterRequest extends AuthLoginRequest{
    private String nickname;

    public AuthRegisterRequest(String nickname, String email, String password) {
        super(email, password);
        this.nickname = nickname;
    }
}
