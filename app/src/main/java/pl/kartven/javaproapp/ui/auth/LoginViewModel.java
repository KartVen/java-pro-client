package pl.kartven.javaproapp.ui.auth;

import static pl.kartven.javaproapp.utils.utility.FieldUtils.EmailValidator;
import static pl.kartven.javaproapp.utils.utility.FieldUtils.PasswordValidator;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.LoginDto;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.utils.listener.LoginEventListener;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class LoginViewModel extends ViewModel implements LoginEventListener {
    private final MainRepository mainRepository;
    private final SessionManager sessionManager;
    private final EmailValidator emailValidator = new EmailValidator();
    private final PasswordValidator passwordValidator = new PasswordValidator();

    @Inject
    public LoginViewModel(MainRepository mainRepository, SessionManager sessionManager) {
        this.mainRepository = mainRepository;
        this.sessionManager = sessionManager;
    }

    public Resource<AuthDomain> login(String email, String password) {
        Resource<AuthDomain> authDtoResource = mainRepository.getAuthData(
                new LoginDto(email, password)
        );
        if (authDtoResource.isSuccess()) {
            AuthDomain authDto = authDtoResource.getData();
            sessionManager.saveUser(new SessionManager.User(
                    authDto.getNickname(),
                    authDto.getEmail(),
                    authDto.getBearerToken(),
                    authDto.getRefreshToken()
            ));
        }
        return authDtoResource;
    }

    public boolean isUserLogged() {
        return sessionManager.isLoggedIn();
    }
}
