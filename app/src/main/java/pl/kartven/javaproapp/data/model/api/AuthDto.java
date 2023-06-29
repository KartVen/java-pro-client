package pl.kartven.javaproapp.data.model.api;

public class AuthDto {
    private String nickname;
    private String email;
    private String bearerToken;
    private String refreshToken;

    public AuthDto(String nickname, String email, String bearerToken, String refreshToken) {
        this.nickname = nickname;
        this.email = email;
        this.bearerToken = bearerToken;
        this.refreshToken = refreshToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
