package pl.kartven.javaproapp.ui.auth;

import android.os.Bundle;

import java.util.concurrent.Executors;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.databinding.ActivityLoginBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.FormTypeActivity;
import pl.kartven.javaproapp.utils.utility.Resource;

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
        binding.confirmBtn.setOnClickListener(v -> handleLogin());
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
                viewModel::isEmailFieldActivated
        );

        handleField(
                binding.loginEtPassword,
                binding.loginTvPasswordInfo,
                viewModel::updatePasswordState,
                viewModel::getPasswordError,
                viewModel::isPasswordFieldActivated
        );

    }

    private void handleLogin() {
        if (!viewModel.isFieldValidated()) return;
        ActivityUtils.showToast(this, getString(R.string.label_please_wait));
        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<AuthDomain> authDomainResource = viewModel.login(
                    binding.loginEtEmail.getText().toString(),
                    binding.loginEtPassword.getText().toString()
            );
            this.runOnUiThread(() -> {
                if (authDomainResource.isSuccess())
                    ActivityUtils.goToActivity(this, MainActivity.class);
                else
                    ActivityUtils.showToast(this, authDomainResource.getMessage());
            });
        });
    }
}