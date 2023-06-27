package pl.kartven.javaproapp.ui.auth;

import static pl.kartven.javaproapp.utils.utility.FieldUtils.EmailValidator;
import static pl.kartven.javaproapp.utils.utility.FieldUtils.NicknameValidator;
import static pl.kartven.javaproapp.utils.utility.FieldUtils.PasswordValidator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.utils.listener.RegisterEventListener;

@HiltViewModel
public class RegisterViewModel extends ViewModel implements RegisterEventListener {
    private final MainRepository mainRepository;
    private final NicknameValidator nicknameValidator = new NicknameValidator();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PasswordValidator passwordValidator = new PasswordValidator();

    @Inject
    public RegisterViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void updateNicknameState(String value) {
        nicknameValidator.setCurrentState(value).validate();
    }
}
