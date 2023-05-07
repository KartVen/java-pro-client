package pl.kartven.javaproapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.UserNotAuthenticatedException;

import com.google.gson.Gson;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.model.AuthApi;

public class SessionManager {
    private static final String PREF_NAME = "auth_s_pref";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String AUTH_BEARER_PREFIX = "Bearer ";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Inject
    public SessionManager(Context context, Gson gson) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    public void saveUser(User user) {
        String json = gson.toJson(user);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor
                .putString(KEY_USER, json)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public User getUser() {
        String json = sharedPreferences.getString(KEY_USER, null);
        return gson.fromJson(json, User.class);
    }

    public void clear() {
        sharedPreferences.edit()
                .remove(KEY_USER)
                .remove(KEY_IS_LOGGED_IN)
                .apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String createAuthHeader() {
        return isLoggedIn() ? AUTH_BEARER_PREFIX + getUser().bearerToken : "";
    }

    public static class User {
        private final String nickname;
        private final String bearerToken;
        private final String refreshToken;

        public User(String nickname, String bearerToken, String refreshToken) {
            this.nickname = nickname;
            this.bearerToken = bearerToken;
            this.refreshToken = refreshToken;
        }

        public static User map(AuthApi authApi) {
            return new User(authApi.getNickname(), authApi.getBearerToken(), authApi.getRefreshToken());
        }
    }
}

