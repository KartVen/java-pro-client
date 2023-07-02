package pl.kartven.javaproapp.data.model.domain;

import pl.kartven.javaproapp.data.model.api.AuthDto;

public class AuthDomain {
    private String nickname;
    private String email;
    private String bearerToken;
    private String refreshToken;

    public AuthDomain(String nickname, String email, String bearerToken, String refreshToken) {
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

    public static AuthDomain map(AuthDto authDto){
        return new AuthDomain(authDto.getNickname(), authDto.getEmail(), authDto.getBearerToken(), authDto.getRefreshToken());
    }
}
