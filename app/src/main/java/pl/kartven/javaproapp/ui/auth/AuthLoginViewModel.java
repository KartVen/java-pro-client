package pl.kartven.javaproapp.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.AuthLoginRequest;
import pl.kartven.javaproapp.data.repository.AuthRepository;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.validator.LoginFormValidator;
import pl.kartven.javaproapp.util.validator.Validator;

@HiltViewModel
public class AuthLoginViewModel extends ViewModel {
    private final MutableLiveData<FieldState> emailState = new MutableLiveData<>(
            new FieldState(false, "", false, null)
    );
    private final MutableLiveData<FieldState> passwordState = new MutableLiveData<>(
            new FieldState(false, "", false, null)
    );
    private final LoginFormValidator loginFormValidator;
    private final AuthRepository authRepository;
    private final LiveData<Resource<AuthApi>> requestResult = new MutableLiveData<>();

    @Inject
    public AuthLoginViewModel(LoginFormValidator loginFormValidator, AuthRepository authRepository) {
        this.loginFormValidator = loginFormValidator;
        this.authRepository = authRepository;
    }

    public LiveData<FieldState> getEmailFieldState() {
        return emailState;
    }

    public LiveData<FieldState> getPasswordFieldState() {
        return passwordState;
    }

    public void updateEmail(String email) {
        Validator.ValidationResult validationResult = loginFormValidator.validateEmail(email);
        emailState.setValue(
                new FieldState(true, email, validationResult.isSuccess(), validationResult.getErrorMessage())
        );
    }

    public void updatePassword(String password) {
        Validator.ValidationResult validationResult = loginFormValidator.validatePassword(password);
        passwordState.setValue(
                new FieldState(true, password, validationResult.isSuccess(), validationResult.getErrorMessage())
        );
    }

    public boolean isValidate(String email, String password) {
        if (loginFormValidator.validateEmail(email).isSuccess() && loginFormValidator.validatePassword(password).isSuccess()) {
            return true;
        }
        updateEmail(email);
        updatePassword(password);
        return false;
    }

    public void login(String email, String password){
        if (isValidate(email, password)) {
            authRepository.login(new AuthLoginRequest(email, password));
        }
    }

    public LiveData<Resource<AuthApi>> getLoginResult(){
        return authRepository.getAuthResult();
    }
}
