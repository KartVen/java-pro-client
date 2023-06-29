package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionCodesListAdapter;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class CodesFragment extends Fragment {

    private FragmentCodesBinding binding;
    private CodesFragmentViewModel viewModel;

    @Inject
    public CodesFragment() {
        // Required empty public constructor
    }

    public static CodesFragment newInstance() {
        CodesFragment fragment = new CodesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = BaseActivity.initViewModel(this, CodesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCodesBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewSection = binding.fragmentCodesRv;

        recyclerViewSection.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Resource<List<SectionDomain>> data = viewModel.getSectionsOfTopic(
                State.<TopicDomain>getState().getData().getId()
        );

        SectionCodesListAdapter adapter = new SectionCodesListAdapter(ListUtils.extractList(data, requireContext()));
        adapter.setItemExpandEvent((model, position) -> ListUtils.extractList(viewModel.getCodesOfSection(model.getId())));
        recyclerViewSection.setAdapter(adapter);
    }
}