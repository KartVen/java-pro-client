package pl.kartven.javaproapp.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.AuthLoginRequest;
import pl.kartven.javaproapp.data.model.AuthRegisterRequest;
import pl.kartven.javaproapp.data.repository.AuthRepository;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.validator.LoginFormValidator;
import pl.kartven.javaproapp.util.validator.RegisterFormValidator;
import pl.kartven.javaproapp.util.validator.Validator;

@HiltViewModel
public class AuthRegisterViewModel extends ViewModel {
    private final MutableLiveData<FieldState> nicknameState = new MutableLiveData<>(
            new FieldState(false, "", false, null)
    );
    private final MutableLiveData<FieldState> emailState = new MutableLiveData<>(
            new FieldState(false, "", false, null)
    );
    private final MutableLiveData<FieldState> passwordState = new MutableLiveData<>(
            new FieldState(false, "", false, null)
    );
    private final RegisterFormValidator registerFormValidator;
    private final AuthRepository authRepository;
    private final LiveData<Resource<AuthApi>> requestResult = new MutableLiveData<>();

    @Inject
    public AuthRegisterViewModel(RegisterFormValidator registerFormValidator, AuthRepository authRepository) {
        this.registerFormValidator = registerFormValidator;
        this.authRepository = authRepository;
    }

    public LiveData<FieldState> getNicknameFieldState() {
        return emailState;
    }

    public LiveData<FieldState> getEmailFieldState() {
        return emailState;
    }

    public LiveData<FieldState> getPasswordFieldState() {
        return passwordState;
    }

    public LiveData<Resource<AuthApi>> getRequestResult() {
        return requestResult;
    }

    public void updateNickname(String nickname) {
        Validator.ValidationResult validationResult = registerFormValidator.validateNickname(nickname);
        nicknameState.setValue(
                new FieldState(true, nickname, validationResult.isSuccess(), validationResult.getErrorMessage())
        );
    }

    public void updateEmail(String email) {
        Validator.ValidationResult validationResult = registerFormValidator.validateEmail(email);
        emailState.setValue(
                new FieldState(true, email, validationResult.isSuccess(), validationResult.getErrorMessage())
        );
    }

    public void updatePassword(String password) {
        Validator.ValidationResult validationResult = registerFormValidator.validatePassword(password);
        passwordState.setValue(
                new FieldState(true, password, validationResult.isSuccess(), validationResult.getErrorMessage())
        );
    }

    public boolean isValidate(String nickname, String email, String password) {
        if (registerFormValidator.validateNickname(nickname).isSuccess() && registerFormValidator.validateEmail(email).isSuccess() && registerFormValidator.validatePassword(password).isSuccess()) {
            return true;
        }
        updateNickname(nickname);
        updateEmail(email);
        updatePassword(password);
        return false;
    }

    public void register(String nickname, String email, String password) {
        if (isValidate(nickname, email, password)) {
            authRepository.register(new AuthRegisterRequest(nickname, email, password));
        }
    }

    public LiveData<Resource<AuthApi>> getRegisterResult() {
        return authRepository.getAuthResult();
    }
}
