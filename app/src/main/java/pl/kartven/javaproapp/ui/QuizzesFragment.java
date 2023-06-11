package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.HiltAndroidApp;
import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentQuizzesBinding;
import pl.kartven.javaproapp.ui.adapter.QuizListAdapter;
import pl.kartven.javaproapp.util.ActivityUtility;
import pl.kartven.javaproapp.util.Constants;
import pl.kartven.javaproapp.util.Resource;

@AndroidEntryPoint
public class QuizzesFragment extends Fragment implements ActivityUtility {

    private FragmentQuizzesBinding binding;
    private QuizzesFragmentViewModel viewModel;
    private TopicDomain topic;

    @Inject
    public QuizzesFragment() {
        // Required empty public constructor
    }

    public static QuizzesFragment newInstance(TopicDomain topic) {
        QuizzesFragment fragment = new QuizzesFragment();
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
        viewModel = new ViewModelProvider(this).get(QuizzesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.fragmentQuizzesRecyclerView1;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        Resource<List<QuizDomain>> data = viewModel.getQuizzesOfTopic(topic.getId());

        QuizListAdapter adapter = new QuizListAdapter(getList(data));
        adapter.setItemClicked((model, position) -> {
            //Intent intent = new Intent(getContext(), CodesDetailsActivity.class);
            showToast(getContext(), "Go to `quiz` " + model.getId());
            //intent.putExtra(Constants.TOPIC_MODEL_NAME, model);
            //startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    private List<QuizDomain> getList(Resource<List<QuizDomain>> data) {
        if (data == null) return Collections.emptyList();
        if (data.isSuccess()) return data.getData();
        return Collections.emptyList();
    }
}