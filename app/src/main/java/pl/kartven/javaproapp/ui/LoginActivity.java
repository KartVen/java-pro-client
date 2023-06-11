package pl.kartven.javaproapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityLoginBinding;
import pl.kartven.javaproapp.util.ActivityUtility;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginConfirmBtn.setOnClickListener(v -> goToActivity(this, MainActivity.class));
        binding.loginRegisterBtn.setOnClickListener(v -> goToActivity(this, RegisterActivity.class));
    }
}