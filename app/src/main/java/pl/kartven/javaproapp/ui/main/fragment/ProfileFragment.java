package pl.kartven.javaproapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Optional;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.databinding.FragmentProfileBinding;
import pl.kartven.javaproapp.ui.main.MainViewModel;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.SessionManager;

public class ProfileFragment extends BaseFragment {
    private MainViewModel mainViewModel;

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getActivityViewModel(MainViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        Optional.ofNullable((FloatingActionButton) requireActivity().findViewById(R.id.main_coordinator_fab))
                .ifPresent(o -> o.setVisibility(View.INVISIBLE));
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initContent() {
        super.initContent();
        AuthDomain loggedUser = mainViewModel.getLoggedUser().getData();
        binding.profileTvEmail.setText(loggedUser.getEmail());
        binding.profileTvNickname.setText(loggedUser.getNickname());
        binding.profileTvLoggedTime.setText(loggedUser.getLoggedDate().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}