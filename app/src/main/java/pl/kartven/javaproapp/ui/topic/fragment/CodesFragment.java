package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.ui.topic.TopicViewModel;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionCodesListAdapter;
import pl.kartven.javaproapp.utils.listener.RVItemExpandListener;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CodesFragment extends BaseFragment {
    private TopicViewModel topicViewModel;
    private FragmentCodesBinding binding;
    private CodesViewModel viewModel;
    private final ExecutorService executor;

    @Inject
    public CodesFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = getActivityViewModel(TopicViewModel.class);
        viewModel = initViewModel(CodesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCodesBinding.inflate(inflater, container, false);
        initRecyclerView(binding.fCodesRvCodesBase);
        return binding.getRoot();
    }

    private void initRecyclerView(RecyclerView recyclerViewSection) {
        recyclerViewSection.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        SectionCodesListAdapter adapter = new SectionCodesListAdapter(new ArrayList<>(), executor);

        executor.execute(() -> {
            Resource<List<SectionDomain>> listResource = viewModel.getSectionsOfTopic(
                    topicViewModel.getTopicDomain().getId()
            );
            requireActivity().runOnUiThread(() -> adapter.updateList(ListUtils.extractList(listResource, requireContext())));
        });

        adapter.setItemExpandEvent(new RVItemExpandListener<SectionDomain, List<CodeDomain>>() {
            @Override
            public List<CodeDomain> onExpand(SectionDomain model, int position) {
                return ListUtils.extractList(viewModel.getCodesOfSection(model.getId()));
            }
        });
        recyclerViewSection.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}