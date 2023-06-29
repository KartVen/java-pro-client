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
import pl.kartven.javaproapp.databinding.FragmentLinksBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionLinksListAdapter;
import pl.kartven.javaproapp.ui.topic.link.LinkViewActivity;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class LinksFragment extends Fragment {

    private FragmentLinksBinding binding;
    private LinksFragmentViewModel viewModel;

    @Inject
    public LinksFragment() {
        // Required empty public constructor
    }

    public static LinksFragment newInstance() {
        return new LinksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = BaseActivity.initViewModel(requireActivity(), LinksFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewSection = binding.fragmentLinksRv;

        recyclerViewSection.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Resource<List<SectionDomain>> data = viewModel.getSectionsOfTopic(
                State.<TopicDomain>getState().getData().getId()
        );

        SectionLinksListAdapter adapter = new SectionLinksListAdapter(ListUtils.extractList(data, requireContext()));
        adapter.setItemExpandEvent((model, position) -> ListUtils.extractList(viewModel.getLinksOfSection(model.getId())));
        adapter.setNestedItemClickEvent((model, position) -> ActivityUtils.goToActivity(requireContext(), LinkViewActivity.class, intent -> {
            intent.putExtra(Constant.Extra.LINK_MODEL, model);
        }));
        recyclerViewSection.setAdapter(adapter);
    }
}