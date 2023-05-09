package pl.kartven.javaproapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.AuthLoginRequest;
import pl.kartven.javaproapp.data.model.AuthRegisterRequest;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;
import pl.kartven.javaproapp.util.adapter.CallbackAdapter;
import retrofit2.Call;
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

    public void login(AuthLoginRequest body) {
        authorize(backendApi.login(body));
        /*authResult.postValue(new Resource.Success<>(Mock.authApi));
        sessionManager.saveUser(SessionManager.User.map(Mock.authApi));*/
    }

    public void register(AuthRegisterRequest body) {
        authorize(backendApi.register(body));
        /*authResult.postValue(new Resource.Success<>(Mock.authApi));
        sessionManager.saveUser(SessionManager.User.map(Mock.authApi));*/
    }

    private void authorize(Call<AuthApi> call){
        call.enqueue(new CallbackAdapter<AuthApi>() {
            @Override
            public void onResponse(@NonNull Call<AuthApi> call, @NonNull Response<AuthApi> response) {
                authResult.postValue(onResponse(response).fold(
                        Resource.Error::new,
                        authApi -> {
                            sessionManager.saveUser(SessionManager.User.map(authApi));
                            return new Resource.Success<>(authApi);
                        }
                ));
            }

            @Override
            public void onFailure(@NonNull Call<AuthApi> call, @NonNull Throwable t) {
                onFailure(authResult);
            }
        });
    }
}
