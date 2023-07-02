package pl.kartven.javaproapp.data.model.api.request;

public class RegisterDto extends LoginDto {
    private String nickname;

    public RegisterDto(String nickname, String email, String password) {
        super(email, password);
        this.nickname = nickname;
    }
}
