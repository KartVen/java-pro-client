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
import pl.kartven.javaproapp.Constants;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.data.repository.AuthRepository;
import pl.kartven.javaproapp.data.repository.LectureRespository;
import pl.kartven.javaproapp.data.repository.RangeRepository;
import pl.kartven.javaproapp.util.validator.LoginFormValidator;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class BeanModule {

    @Provides
    @Singleton
    public BackendApi provideBackendApi(){
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
    public AuthRepository provideAuthRepository(BackendApi api, SessionManager sessionManager){
        return new AuthRepository(api, sessionManager);
    }

    @Provides
    @Singleton
    public SessionManager provideSessionManager(Context context, Gson gson) {
        return new SessionManager(context, gson);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    public LoginFormValidator provideEmailValidator(Resources resources){
        return new LoginFormValidator(resources);
    }

    @Provides
    @Singleton
    public RangeRepository provideRangeRepository(BackendApi api, SessionManager sessionManager){
        return new RangeRepository(api, sessionManager);
    }

    @Provides
    @Singleton
    public LectureRespository provideLectureRepository(BackendApi api, SessionManager sessionManager){
        return new LectureRespository(api, sessionManager);
    }
}
