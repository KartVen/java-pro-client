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

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentLinksBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionLinksListAdapter;
import pl.kartven.javaproapp.ui.topic.link.LinkActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class LinksFragment extends BaseFragment {

    private FragmentLinksBinding binding;
    private LinksViewModel viewModel;

    @Inject
    public LinksFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Resource<List<SectionDomain>> data = viewModel.getSectionsOfTopic(
                State.<TopicDomain>getState().getData().getId()
        );

        SectionLinksListAdapter adapter = new SectionLinksListAdapter(ListUtils.extractList(data, requireContext()));
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