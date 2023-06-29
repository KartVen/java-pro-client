package pl.kartven.javaproapp.ui.auth;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityLoginBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.FormTypeActivity;

public class LoginActivity extends FormTypeActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(LoginViewModel.class);

        if (viewModel.isUserLogged()) handleError(false,
                () -> ActivityUtils.goToActivity(this, MainActivity.class)
        );

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.confirmBtn.setOnClickListener(v -> handleAuth());
        binding.loginTvRegister.setOnClickListener(v -> ActivityUtils.goToActivity(this, RegisterActivity.class));
    }

    @Override
    protected void initContent() {
        super.initContent();

        handleField(
                binding.loginEtEmail,
                binding.loginTvEmailInfo,
                viewModel::updateEmailState,
                viewModel::getEmailError,
                viewModel::isEmailFieldActived
        );

        handleField(
                binding.loginEtPassword,
                binding.loginTvPasswordInfo,
                viewModel::updatePasswordState,
                viewModel::getPasswordError,
                viewModel::isPasswordFieldActived
        );

    }

    private void handleAuth() {
        viewModel.login(
                binding.loginEtEmail.getText().toString(),
                binding.loginEtPassword.getText().toString()
        );
        ActivityUtils.goToActivity(this, MainActivity.class);
    }
}