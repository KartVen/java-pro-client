package pl.kartven.javaproapp.ui.auth;

import android.os.Bundle;

import java.util.concurrent.Executors;

import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.databinding.ActivityRegisterBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.FormTypeActivity;
import pl.kartven.javaproapp.utils.utility.Resource;

public class RegisterActivity extends FormTypeActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(RegisterViewModel.class);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.confirmBtn.setOnClickListener(v -> handleRegister());
        binding.registerTvBack.setOnClickListener(v -> ActivityUtils.goToActivity(this, MainActivity.class));
    }

    @Override
    protected void initContent() {
        super.initContent();
        handleField(
                binding.registerEtNickname,
                binding.registerTvNicknameInfo,
                viewModel::updateNicknameState,
                viewModel::getNicknameError,
                viewModel::isNicknameFieldActivated
        );

        handleField(
                binding.registerEtEmail,
                binding.registerTvEmailInfo,
                viewModel::updateEmailState,
                viewModel::getEmailError,
                viewModel::isEmailFieldActivated
        );

        handleField(
                binding.registerEtPassword,
                binding.registerTvPasswordInfo,
                viewModel::updatePasswordState,
                viewModel::getPasswordError,
                viewModel::isPasswordFieldActivated
        );
    }

    private void handleRegister() {
        if (!viewModel.isFieldValidated()) return;
        ActivityUtils.showToast(this, getString(R.string.label_please_wait));
        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<AuthDomain> authDomainResource = viewModel.register(
                    binding.registerEtNickname.getText().toString(),
                    binding.registerEtEmail.getText().toString(),
                    binding.registerEtPassword.getText().toString()
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