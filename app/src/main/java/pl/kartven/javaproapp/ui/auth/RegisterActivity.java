package pl.kartven.javaproapp.ui.auth;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityRegisterBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.FormTypeActivity;

public class RegisterActivity extends FormTypeActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = initViewModel(RegisterViewModel.class);

        initActions();
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.confirmBtn.setOnClickListener(v -> ActivityUtils.goToActivity(this, MainActivity.class));
        binding.registerTvBack.setOnClickListener(v -> ActivityUtils.goToActivity(this, LoginActivity.class));
    }

    @Override
    protected void initContent() {
        super.initContent();
        handleField(
                binding.registerEtNickname,
                binding.registerTvNicknameInfo,
                viewModel::updateNicknameState,
                viewModel::getNicknameError,
                viewModel::isNicknameFieldActived
        );

        handleField(
                binding.registerEtEmail,
                binding.registerTvEmailInfo,
                viewModel::updateEmailState,
                viewModel::getEmailError,
                viewModel::isEmailFieldActived
        );

        handleField(
                binding.registerEtPassword,
                binding.registerTvPasswordInfo,
                viewModel::updatePasswordState,
                viewModel::getPasswordError,
                viewModel::isPasswordFieldActived
        );
    }
}