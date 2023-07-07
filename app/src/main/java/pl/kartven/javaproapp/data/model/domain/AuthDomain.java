package pl.kartven.javaproapp.data.model.domain;

import java.util.Date;

import pl.kartven.javaproapp.data.model.api.AuthApi;

public class AuthDomain {
    private final Long id;
    private final String nickname;
    private final String email;
    private final String bearerToken;
    private final String refreshToken;
    private final Date loggedDate = new Date();

    public AuthDomain(Long id, String nickname, String email, String bearerToken, String refreshToken) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.bearerToken = bearerToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static AuthDomain map(AuthApi authApi) {
        return new AuthDomain(authApi.getId(), authApi.getNickname(), authApi.getEmail(), authApi.getBearerToken(), authApi.getRefreshToken());
    }

    public Date getLoggedDate() {
        return loggedDate;
    }
}
