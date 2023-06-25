package pl.kartven.javaproapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.kartven.javaproapp.data.BackendApi;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.MainRepositoryBackend;
import pl.kartven.javaproapp.data.MainRepositoryMock;
import pl.kartven.javaproapp.utils.utility.SessionManager;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("deprecated")
@HiltAndroidApp
public class JavaProApplication extends Application {

    @Module
    @InstallIn(SingletonComponent.class)
    public static class Bean {
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
                    .baseUrl(BackendApi.API.getUrl())
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
        public MainRepository mainRepository(BackendApi backendApi, SessionManager sessionManager) {
            return new MainRepositoryMock();
        }
    }
}

