package pl.kartven.javaproapp.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.MainActivity;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.ActivityAuthLoginBinding;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.util.adapter.TextWatcherCustomAdapter;
import pl.kartven.javaproapp.util.ActivityUtility;

@AndroidEntryPoint
public class AuthLoginActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityAuthLoginBinding binding;
    private AuthLoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AuthLoginViewModel.class);

        final AppCompatEditText
                emailET = binding.loginEmailEt,
                passwordET = binding.loginPasswordEt;
        final Button confirmBtn = binding.loginConfirmBtn;

        emailET.addTextChangedListener(new TextWatcherCustomAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateEmail(String.valueOf(emailET.getText()));
            }
        });

        viewModel.getEmailFieldState().observe(this, new Observer<FieldState>() {
            @Override
            public void onChanged(FieldState fieldState) {
                if (fieldState != null && fieldState.isActivated()) {
                    emailET.setError(fieldState.getError());
                }
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
            viewModel.login(
                    String.valueOf(emailET.getText()),
                    String.valueOf(passwordET.getText())
            );
        });

        viewModel.getLoginResult().observe(this, authApiResource -> {
            if (authApiResource.isSuccess()) {
                showToast(this, getString(R.string.login_succesfully));
                goToActivity(this, MainActivity.class);
            } else {
                showToast(this, authApiResource.getMessage());
            }
        });

        binding.loginRegisterBtn.setOnClickListener(l -> goToActivity(this, AuthRegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
    }

}