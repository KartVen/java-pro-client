package pl.kartven.javaproapp.ui.profile;

import android.os.Bundle;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.ActivityProfileBinding;
import pl.kartven.javaproapp.utils.base.BaseActivity;

@AndroidEntryPoint
public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = initViewModel(ProfileViewModel.class);

        initActions();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.profileHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
    }
}