package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.ui.topic.TopicViewModel;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SectionCodesListAdapter;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class CodesFragment extends BaseFragment {
    private TopicViewModel topicViewModel;
    private FragmentCodesBinding binding;
    private CodesViewModel viewModel;
    private final ExecutorService executor;
    private SectionCodesListAdapter codesListAdapter;

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
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initContent() {
        super.initContent();
        initRecyclerView(binding.fCodesRvCodesBase);
    }

    private void initRecyclerView(RecyclerView recyclerViewSection) {
        recyclerViewSection.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        codesListAdapter = new SectionCodesListAdapter(new ArrayList<>(), executor);

        executeBackend(codesListAdapter);

        codesListAdapter.setItemExpandEvent((model, position) -> ListUtils.extractList(viewModel.getCodesOfSection(model.getId())));
        recyclerViewSection.setAdapter(codesListAdapter);
    }

    private void executeBackend(SectionCodesListAdapter adapter) {
        executor.execute(() -> {
            Resource<List<SectionDomain>> listResource = viewModel.getSectionsOfTopic(
                    topicViewModel.getTopicDomain().getId()
            );
            requireActivity().runOnUiThread(() -> adapter.updateList(ListUtils.extractList(listResource, requireContext())));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class DialogData {
        private String name;
        private String code;
        private Long id;

        public boolean isValidated() {
            return StringUtils.isNotEmpty(this.name) && StringUtils.isNotEmpty(this.code) && Objects.nonNull(this.id);
        }
    }
}