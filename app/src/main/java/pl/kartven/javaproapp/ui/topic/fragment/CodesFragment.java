package pl.kartven.javaproapp.ui.topic.fragment;

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
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionCodesListAdapter;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class CodesFragment extends BaseFragment {

    private FragmentCodesBinding binding;
    private CodesViewModel viewModel;

    @Inject
    public CodesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(CodesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCodesBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewSection = binding.fCodesRvCodesBase;

        recyclerViewSection.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Resource<List<SectionDomain>> data = viewModel.getSectionsOfTopic(
                State.<TopicDomain>getState().getData().getId()
        );

        SectionCodesListAdapter adapter = new SectionCodesListAdapter(ListUtils.extractList(data, requireContext()));
        adapter.setItemExpandEvent((model, position) -> ListUtils.extractList(viewModel.getCodesOfSection(model.getId())));
        recyclerViewSection.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}