package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentQuizzesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.QuizListAdapter;
import pl.kartven.javaproapp.utils.resource.Resource;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class QuizzesFragment extends Fragment {

    private FragmentQuizzesBinding binding;
    private QuizzesFragmentViewModel viewModel;
    private TopicDomain topic;

    @Inject
    public QuizzesFragment() {
        // Required empty public constructor
    }

    public static QuizzesFragment newInstance() {
        return new QuizzesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuizzesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        Long topicId = State.<TopicDomain>getState().getData().getId();

        RecyclerView recyclerView = binding.fragmentQuizzesRv;
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(recyclerView, viewModel.getQuizzesOfTopic(topicId));

        RecyclerView recyclerViewNewQuiz = binding.fragmentQuizzesNewQuizRv;
        recyclerViewNewQuiz.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(recyclerViewNewQuiz, viewModel.getQuizzesOfTopic(topicId));
    }

    private void setAdapter(RecyclerView recyclerView, Resource<List<QuizDomain>> data) {
        QuizListAdapter adapter = new QuizListAdapter(ListUtils.extractList(data, requireContext()));
        adapter.setItemClicked((model, position) ->
                ActivityUtils.goToActivity(requireContext(), null, intent -> {
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.Extra.QUIZ_MODEL, model);
                }));
        recyclerView.setAdapter(adapter);
    }
}