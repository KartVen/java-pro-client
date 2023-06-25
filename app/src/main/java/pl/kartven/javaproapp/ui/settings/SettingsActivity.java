package pl.kartven.javaproapp.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityProfileBinding;
import pl.kartven.javaproapp.databinding.ActivitySettingsBinding;
import pl.kartven.javaproapp.ui.auth.LoginActivity;
import pl.kartven.javaproapp.ui.profile.ProfileViewModel;
import pl.kartven.javaproapp.utils.base.BaseActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    private SettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = initViewModel(SettingsViewModel.class);

        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.settingsHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
        binding.settingsCardViewLogout.setOnClickListener(v -> {
            viewModel.logout();
            ActivityUtils.goToActivity(this, LoginActivity.class);
        });
    }
}