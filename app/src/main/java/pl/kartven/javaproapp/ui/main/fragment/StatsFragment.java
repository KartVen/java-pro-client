package pl.kartven.javaproapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentStatsBinding;
import pl.kartven.javaproapp.ui.main.MainViewModel;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class StatsFragment extends BaseFragment {
    private MainViewModel mainViewModel;
    private FragmentStatsBinding binding;
    private StatsFragmentViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public StatsFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getActivityViewModel(MainViewModel.class);
        viewModel = initViewModel(StatsFragmentViewModel.class);
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
    protected void initContent() {
        super.initContent();
        executor.execute(() -> {
            Resource<List<TopicDomain>> listResource = viewModel.getMyTopics();
            requireActivity().runOnUiThread(() -> {
                binding.statsTvTopicsAdd.setText(listResource.isSuccess() ? String.valueOf(listResource.getData().size()) : "");
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}