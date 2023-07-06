package pl.kartven.javaproapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentHomeBinding;
import pl.kartven.javaproapp.ui.main.fragment.adapter.TopicListAdapter;
import pl.kartven.javaproapp.ui.topic.TopicActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;
    private final ExecutorService executor;
    private TopicListAdapter topicsAdapter;
    private TopicListAdapter myTopicsAdapter;

    @Inject
    public HomeFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(HomeFragmentViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Optional.ofNullable((FloatingActionButton) requireActivity().findViewById(R.id.main_coordinator_fab))
                .ifPresent(o -> o.setVisibility(View.VISIBLE));
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initContent() {
        super.initContent();
        topicsAdapter = initRecyclerView(binding.fHomeRvTopicBase, viewModel::getTopics);
        myTopicsAdapter = initRecyclerView(binding.fHomeRvTopicByYou, viewModel::getMyTopics);
    }

    private TopicListAdapter initRecyclerView(RecyclerView recyclerView, Supplier<Resource<List<TopicDomain>>> resourceSupplier) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );
        TopicListAdapter adapter = new TopicListAdapter(new ArrayList<>());
        executeBackend(adapter, resourceSupplier);

        adapter.setItemClicked((model, position) -> {
            ActivityUtils.goToActivity(
                    requireContext(),
                    TopicActivity.class,
                    intent -> intent.putExtra(Constant.Extra.TOPIC_MODEL, model)
            );
            requireActivity().finish();
        });
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void executeBackend(TopicListAdapter adapter, Supplier<Resource<List<TopicDomain>>> resourceSupplier) {
        executor.execute(() -> {
            Resource<List<TopicDomain>> listResource = resourceSupplier.get();
            requireActivity().runOnUiThread(() -> {
                adapter.updateList(ListUtils.extractList(listResource, requireContext()));
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