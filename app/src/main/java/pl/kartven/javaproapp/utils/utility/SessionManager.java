package pl.kartven.javaproapp.utils.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.model.domain.AuthDomain;

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

    public void saveUser(AuthDomain user) {
        String json = gson.toJson(user);
        sharedPreferences.edit()
                .putString(KEY_USER, json)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public Resource<AuthDomain> getUser() {
        String json = sharedPreferences.getString(KEY_USER, null);
        return isLoggedIn() ?
                new Resource.Success<>(gson.fromJson(json, AuthDomain.class)) :
                new Resource.Error<>("");
    }

    public String getBearerToken(){
        return AUTH_BEARER_PREFIX + this.getUser().getData().getBearerToken();
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

}

