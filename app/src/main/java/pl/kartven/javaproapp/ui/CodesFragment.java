package pl.kartven.javaproapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Try;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentCodesBinding;
import pl.kartven.javaproapp.ui.adapter.SectionListAdapter;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.Constants;
import pl.kartven.javaproapp.util.Resource;

@AndroidEntryPoint
public class CodesFragment extends Fragment implements ActivityUtility {

    private FragmentCodesBinding binding;
    private CodesFragmentViewModel viewModel;
    private TopicDomain topic;

    @Inject
    public CodesFragment() {
        // Required empty public constructor
    }

    public static CodesFragment newInstance(TopicDomain topic) {
        CodesFragment fragment = new CodesFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TOPIC_MODEL_NAME, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Try.of(this::getArguments)
                .map(bundle -> (TopicDomain) bundle.getSerializable(Constants.TOPIC_MODEL_NAME))
                .onSuccess(topicDomain -> topic = topicDomain);
        viewModel = new ViewModelProvider(this).get(CodesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCodesBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.fragmentCodesRecyclerView1;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        Resource<List<SectionDomain>> data = viewModel.getSectionsOfTopic(topic.getId());

        SectionListAdapter adapter = new SectionListAdapter(getList(data));
        adapter.setItemClicked((model, position) -> {
            //Intent intent = new Intent(getContext(), CodesDetailsActivity.class);
            showToast(getContext(), "Go to `section` " + model.getId());
            //intent.putExtra(Constants.TOPIC_MODEL_NAME, model);
            //startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private List<SectionDomain> getList(Resource<List<SectionDomain>> data) {
        if (data == null) return Collections.emptyList();
        if (data.isSuccess()) return data.getData();
        return Collections.emptyList();
    }
}