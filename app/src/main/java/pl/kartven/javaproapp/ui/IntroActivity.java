package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityIntroBinding;
import pl.kartven.javaproapp.ui.auth.LoginActivity;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;

public class IntroActivity extends BaseActivity {

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.mainBtn.setOnClickListener(v -> ActivityUtils.goToActivity(this, LoginActivity.class));
    }
}