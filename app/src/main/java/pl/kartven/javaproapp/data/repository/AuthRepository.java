package pl.kartven.javaproapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.Mock;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.AuthLoginRequest;
import pl.kartven.javaproapp.data.model.AuthRegisterRequest;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthRepository {
    private final BackendApi backendApi;
    private final MutableLiveData<Resource<AuthApi>> authResult;
    private final SessionManager sessionManager;

    @Inject
    public AuthRepository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
        this.authResult = new MutableLiveData<>();
    }

    public LiveData<Resource<AuthApi>> getAuthResult() {
        return authResult;
    }

    public LiveData<Resource<AuthApi>> login(AuthLoginRequest body) {
        backendApi.login(body).enqueue(new Callback<AuthApi>() {
            @Override
            public void onResponse(@NonNull Call<AuthApi> call, @NonNull Response<AuthApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    authResult.postValue(new Resource.Success<>(response.body()));
                    sessionManager.saveUser(SessionManager.User.map(response.body()));
                } else {
                    authResult.postValue(new Resource.Error<>(response.message(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthApi> call, @NonNull Throwable t) {
                authResult.postValue(new Resource.Error<>("Couldn't reach server", null));
            }
        });
        /*authResult.postValue(new Resource.Success<>(Mock.authApi));
        sessionManager.saveUser(SessionManager.User.map(Mock.authApi));*/
        return authResult;
    }

    public LiveData<Resource<AuthApi>> register(AuthRegisterRequest body) {
        backendApi.register(body).enqueue(new Callback<AuthApi>() {
            @Override
            public void onResponse(@NonNull Call<AuthApi> call, @NonNull Response<AuthApi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    authResult.postValue(new Resource.Success<>(response.body()));
                    sessionManager.saveUser(SessionManager.User.map(response.body()));
                } else {
                    authResult.postValue(new Resource.Error<>(response.message(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthApi> call, @NonNull Throwable t) {
                authResult.postValue(new Resource.Error<>("Couldn't reach server", null));
            }
        });
        /*authResult.postValue(new Resource.Success<>(Mock.authApi));
        sessionManager.saveUser(SessionManager.User.map(Mock.authApi));*/
        return authResult;
    }
}
