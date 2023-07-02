package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class SessionManager {
    private static final String PREF_NAME = "auth_s_pref";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String AUTH_BEARER_PREFIX = "Bearer ";
    private static final String LOGGED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            LOGGED_DATE_FORMAT, Locale.getDefault()
    );

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    public SessionManager(Context context, Gson gson) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    public void saveUser(User user) {
        String json = gson.toJson(user);
        sharedPreferences.edit()
                .putString(KEY_USER, json)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public Resource<User> getUser() {
        String json = sharedPreferences.getString(KEY_USER, null);
        return isLoggedIn() ?
                new Resource.Success<>(gson.fromJson(json, User.class)) :
                new Resource.Error<>("");
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

    public static class User {
        private final String nickname;
        private final String email;
        private final String bearerToken;
        private final String refreshToken;
        private final Date loggedDate = new Date();

        public User(String nickname, String email, String bearerToken, String refreshToken) {
            this.nickname = nickname;
            this.email = email;
            this.bearerToken = bearerToken;
            this.refreshToken = refreshToken;
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

        public Date getLoggedDate() {
            return loggedDate;
        }
    }
}

