package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityIntroBinding;
import pl.kartven.javaproapp.ui.auth.LoginActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;

@AndroidEntryPoint
public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
    }

    private void initActions() {
        binding.mainBtn.setOnClickListener(v -> ActivityUtils.goToActivity(this, LoginActivity.class));
    }
}