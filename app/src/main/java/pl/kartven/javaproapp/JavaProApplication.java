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
import pl.kartven.javaproapp.utils.utility.SessionManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("deprecated")
@HiltAndroidApp
public class JavaProApplication extends Application {
    private final static ApiUrlType API_URL_TYPE = ApiUrlType.DORMITORY;

    private enum ApiUrlType {
        DORMITORY("http://192.168.10.104:8444"),
        COMPANY("http://19.16.13.171:8444"),
        PHONE("http://192.168.43.239:8444"),
        ALWAYSDATA("http://krystianus.alwaysdata.net");

        private final String url;

        ApiUrlType(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    @Module
    @InstallIn(SingletonComponent.class)
    public static class Bean {

        private Retrofit retrofit() {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                httpClient.addInterceptor(logging);
            }

            /*int cacheSize = 10; //MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize * (1024 * 1024));
            CacheInterceptor cacheInterceptor = new CacheInterceptor(cache);
            httpClient.cache(cache).addInterceptor(cacheInterceptor);*/

            return new Retrofit.Builder()
                    .baseUrl(API_URL_TYPE.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        @Provides
        @Singleton
        public BackendApi backendApi() {
            return retrofit().create(BackendApi.class);
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
        public SessionManager sessionManager(Context context, Gson gson) {
            return new SessionManager(context, gson);
        }

        @Provides
        @Singleton
        public MainRepository mainRepository(BackendApi backendApi, SessionManager sessionManager) {
            //return new MainRepositoryMock();
            return new MainRepositoryBackend(backendApi, sessionManager);
        }
    }
}

