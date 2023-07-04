package pl.kartven.javaproapp.ui.creator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.databinding.FragmentCreatorCodeBinding;
import pl.kartven.javaproapp.ui.creator.CreatorViewModel;
import pl.kartven.javaproapp.utils.utility.BaseFragment;

@AndroidEntryPoint
public class CreatorCodeFragment extends BaseFragment {
    private CreatorViewModel creatorViewModel;
    private FragmentCreatorCodeBinding binding;
    private CreatorCodeViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public CreatorCodeFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatorViewModel = getActivityViewModel(CreatorViewModel.class);
        viewModel = initViewModel(CreatorCodeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatorCodeBinding.inflate(inflater, container, false);
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initContent() {
        super.initContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}