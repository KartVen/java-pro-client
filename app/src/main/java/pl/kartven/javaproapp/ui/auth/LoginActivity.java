package pl.kartven.javaproapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityLoginBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
    }

    private void initActions() {
        binding.confirmBtn.setOnClickListener(v -> ActivityUtils.goToActivity(this, MainActivity.class));
        binding.loginTvRegister.setOnClickListener(v -> ActivityUtils.goToActivity(this, RegisterActivity.class));
    }
}