package pl.kartven.javaproapp.ui.profile;

import android.os.Bundle;

import pl.kartven.javaproapp.databinding.ActivityProfileBinding;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.SessionManager;

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
        initContent();
    }

    @Override
    protected void initActions() {
        super.initActions();
        binding.profileHeaderBackBtnIv.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initContent() {
        super.initContent();
        SessionManager.User user = viewModel.getUser().getData();
        binding.profileTvName.setText(user.getNickname());
        binding.profileTvEmail.setText(user.getEmail());
    }
}