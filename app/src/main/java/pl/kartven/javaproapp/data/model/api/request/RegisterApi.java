package pl.kartven.javaproapp.data.model.api.request;

public class RegisterApi extends LoginApi {
    private String nickname;

    public RegisterApi(String nickname, String email, String password) {
        super(email, password);
        this.nickname = nickname;
    }
}
