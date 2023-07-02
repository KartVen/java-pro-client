package pl.kartven.javaproapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Optional;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.FragmentStatsBinding;
import pl.kartven.javaproapp.ui.main.MainViewModel;
import pl.kartven.javaproapp.utils.utility.BaseFragment;

public class StatsFragment extends BaseFragment {
    private MainViewModel mainViewModel;

    private FragmentStatsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getActivityViewModel(MainViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        Optional.ofNullable((FloatingActionButton) requireActivity().findViewById(R.id.main_coordinator_fab))
                .ifPresent(o -> o.setVisibility(View.INVISIBLE));
        initContent();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}