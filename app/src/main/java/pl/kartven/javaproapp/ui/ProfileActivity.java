package pl.kartven.javaproapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityProfileBinding;
import pl.kartven.javaproapp.util.ActivityUtility;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        initButtonAction();
    }

    private void initButtonAction() {
        binding.profileImageView1.setOnClickListener(v -> onBackPressed());
        binding.profileCardView1.setOnClickListener(v -> {
            viewModel.logout();
            goToActivity(this, MainActivity.class);
        });
    }
}