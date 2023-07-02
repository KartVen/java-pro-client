package pl.kartven.javaproapp.ui.topic.fragment;

import android.content.Intent;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.databinding.FragmentLinksBinding;
import pl.kartven.javaproapp.ui.topic.TopicViewModel;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionLinksListAdapter;
import pl.kartven.javaproapp.ui.topic.link.LinkActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class LinksFragment extends BaseFragment {
    private TopicViewModel topicViewModel;
    private FragmentLinksBinding binding;
    private LinksViewModel viewModel;
    private final Executor executor;

    @Inject
    public LinksFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = getActivityViewModel(TopicViewModel.class);
        viewModel = initViewModel(LinksViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView rvBase = binding.fLinksRvBase;

        rvBase.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        SectionLinksListAdapter adapter = new SectionLinksListAdapter(new ArrayList<>(), executor);

        executor.execute(() -> {
            Resource<List<SectionDomain>> listResource = viewModel.getSectionsOfTopic(
                    topicViewModel.getTopicDomain().getId()
            );
            requireActivity().runOnUiThread(() -> {
                adapter.updateList(ListUtils.extractList(listResource, requireContext()));
            });
        });

        adapter.setItemExpandEvent((model, position) -> ListUtils.extractList(viewModel.getLinksOfSection(model.getId())));
        adapter.setNestedItemClickEvent((model, position) ->
                ActivityUtils.goToActivity(requireActivity(), LinkActivity.class, intent -> {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.Extra.LINK_MODEL, model);
                })
        );
        rvBase.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}