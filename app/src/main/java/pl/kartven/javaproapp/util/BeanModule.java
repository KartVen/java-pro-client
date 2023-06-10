package pl.kartven.javaproapp.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.kartven.javaproapp.BuildConfig;
import pl.kartven.javaproapp.data.BackendApi;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.util.validator.LoginFormValidator;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class BeanModule {

    @Provides
    @Singleton
    public BackendApi backendApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(logging);
        }

        return new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BACKEND_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(BackendApi.class);
    }

    @Provides
    @Singleton
    public SessionManager sessionManager(Context context, Gson gson) {
        return new SessionManager(context, gson);
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public Context context(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources resources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    public LoginFormValidator loginFormValidator(Resources resources) {
        return new LoginFormValidator(resources);
    }

    @Provides
    @Singleton
    public MainRepository mainRepository(BackendApi backendApi, SessionManager sessionManager) {
        return new MainRepository(backendApi, sessionManager);
    }
}
