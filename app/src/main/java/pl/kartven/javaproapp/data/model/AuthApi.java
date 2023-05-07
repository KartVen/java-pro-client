package pl.kartven.javaproapp.data.model;

public class AuthApi {
    private String nickname;
    private String bearerToken;
    private String refreshToken;

    public AuthApi() {
    }

    public AuthApi(String nickname, String bearerToken, String refreshToken) {
        this.nickname = nickname;
        this.bearerToken = bearerToken;
        this.refreshToken = refreshToken;
    }

    public String getNickname() { return nickname; }

    public String getBearerToken() { return bearerToken; }

    public String getRefreshToken() { return refreshToken; }
}
