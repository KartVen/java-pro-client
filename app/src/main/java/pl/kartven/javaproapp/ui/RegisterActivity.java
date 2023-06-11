package pl.kartven.javaproapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityRegisterBinding;
import pl.kartven.javaproapp.util.ActivityUtility;

public class RegisterActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerConfirmBtn.setOnClickListener(v -> goToActivity(this, MainActivity.class));
        binding.registerLoginBtn.setOnClickListener(v -> goToActivity(this, LoginActivity.class));
    }
}