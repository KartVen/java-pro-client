package pl.kartven.javaproapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.MainActivity;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityAuthLoginBinding;
import pl.kartven.javaproapp.databinding.ActivityAuthRegisterBinding;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.util.TextWatcherCustomAdapter;
import pl.kartven.javaproapp.util.Utility;

@AndroidEntryPoint
public class AuthRegisterActivity extends AppCompatActivity implements Utility {

    private ActivityAuthRegisterBinding binding;
    private AuthRegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AuthRegisterViewModel.class);

        final AppCompatEditText
                nicknameET = binding.registerNicknameEt,
                emailET = binding.registerEmailEt,
                passwordET = binding.registerPasswordEt;
        final Button confirmBtn = binding.registerConfirmBtn;

        nicknameET.addTextChangedListener(new TextWatcherCustomAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateNickname(String.valueOf(nicknameET.getText()));
            }
        });

        viewModel.getNicknameFieldState().observe(this, fieldState -> {
            if (fieldState != null && fieldState.isActivated()) {
                nicknameET.setError(fieldState.getError());
            }
        });

        emailET.addTextChangedListener(new TextWatcherCustomAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateEmail(String.valueOf(emailET.getText()));
            }
        });

        viewModel.getEmailFieldState().observe(this, fieldState -> {
            if (fieldState != null && fieldState.isActivated()) {
                emailET.setError(fieldState.getError());
            }
        });

        passwordET.addTextChangedListener(new TextWatcherCustomAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updatePassword(String.valueOf(passwordET.getText()));
            }
        });

        viewModel.getPasswordFieldState().observe(this, fieldState -> {
            if (fieldState != null && fieldState.isActivated()) {
                passwordET.setError(fieldState.getError());
            }
        });

        emailET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    passwordET.requestFocus();
                    return true;
                }
                return false;
            }
        });

        passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        confirmBtn.setOnClickListener(l -> {
            viewModel.register(
                    String.valueOf(nicknameET.getText()),
                    String.valueOf(emailET.getText()),
                    String.valueOf(passwordET.getText())
            );
        });

        viewModel.getRegisterResult().observe(this, authApiResource -> {
            if (authApiResource.isSuccess()) {
                showToast(this, getString(R.string.register_succesfully));
                goToActivity(this, MainActivity.class);
            } else {
                showToast(this, authApiResource.getMessage());
            }
        });

        binding.registerLoginBtn.setOnClickListener(l -> goToActivity(this, AuthLoginActivity.class));
    }

    @Override
    public void onBackPressed() {
    }

}