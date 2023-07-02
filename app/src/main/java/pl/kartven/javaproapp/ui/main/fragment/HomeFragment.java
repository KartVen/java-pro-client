package pl.kartven.javaproapp.ui.main.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import io.vavr.control.Try;
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

    @Inject
    public HomeFragment() {
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
        initRecyclerView(binding.fHomeRvTopicBase, viewModel::getTopics);
        initRecyclerView(binding.fHomeRvTopicByYou, viewModel::getMyTopics);
    }

    private void initRecyclerView(RecyclerView recyclerView, Supplier<Resource<List<TopicDomain>>> resourceSupplier) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );
        TopicListAdapter adapter = new TopicListAdapter(new ArrayList<>());
        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<List<TopicDomain>> listResource = resourceSupplier.get();
            requireActivity().runOnUiThread(() -> {
                adapter.updateList(ListUtils.extractList(listResource, requireContext()));
            });
        });

        adapter.setItemClicked((model, position) -> {
            ActivityUtils.goToActivity(
                    requireContext(),
                    TopicActivity.class,
                    intent -> intent.putExtra(Constant.Extra.TOPIC_MODEL, model)
            );
            requireActivity().finish();
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}