package pl.kartven.javaproapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityLoginBinding;
import pl.kartven.javaproapp.databinding.ActivityRegisterBinding;
import pl.kartven.javaproapp.ui.main.MainActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
    }

    private void initActions() {
        binding.confirmBtn.setOnClickListener(v -> ActivityUtils.goToActivity(this, MainActivity.class));
        binding.registerTvBack.setOnClickListener(v -> ActivityUtils.goToActivity(this, LoginActivity.class));
    }
}