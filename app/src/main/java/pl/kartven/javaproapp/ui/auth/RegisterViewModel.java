package pl.kartven.javaproapp.ui.auth;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.RegisterDto;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.utils.listener.RegisterEventListener;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class RegisterViewModel extends ViewModel implements RegisterEventListener {
    private final MainRepository mainRepository;
    private final SessionManager sessionManager;

    @Inject
    public RegisterViewModel(MainRepository mainRepository, SessionManager sessionManager) {
        this.mainRepository = mainRepository;
        this.sessionManager = sessionManager;
    }

    public boolean isUserLogged() {
        return sessionManager.isLoggedIn();
    }

    public Resource<AuthDomain> register(String nickname, String email, String password) {
        Resource<AuthDomain> authDtoResource = mainRepository.getAuthData(
                new RegisterDto(nickname, email, password)
        );
        if (authDtoResource.isSuccess()) {
            sessionManager.saveUser(authDtoResource.getData());
        }
        return authDtoResource;
    }

    public boolean isFormValidated() {
        return isFieldValidated();
    }
}
