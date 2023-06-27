package pl.kartven.javaproapp.ui.auth;

import static pl.kartven.javaproapp.utils.utility.FieldUtils.*;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.utils.listener.LoginEventListener;
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

    public void login(String email) {
        sessionManager.saveUser(new SessionManager.User("nickname", email, "", ""));
    }

    public boolean isUserLogged() {
        return sessionManager.isLoggedIn();
    }
}
