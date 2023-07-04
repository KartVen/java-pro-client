package pl.kartven.javaproapp.data.model.api;

public class AuthDto {
    private Long id;
    private String nickname;
    private String email;
    private String bearerToken;
    private String refreshToken;

    public AuthDto(Long id, String nickname, String email, String bearerToken, String refreshToken) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.bearerToken = bearerToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
