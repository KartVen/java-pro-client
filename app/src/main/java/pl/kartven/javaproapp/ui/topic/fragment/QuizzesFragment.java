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
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentQuizzesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.QuizListAdapter;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class QuizzesFragment extends BaseFragment {

    private FragmentQuizzesBinding binding;
    private QuizzesViewModel viewModel;

    @Inject
    public QuizzesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(QuizzesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        Long topicId = State.<TopicDomain>getState().getData().getId();

        RecyclerView rvBase = binding.fQuizzesRvBase;
        rvBase.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(rvBase, viewModel.getQuizzesOfTopic(topicId));

        RecyclerView rvByYou = binding.fQuizzesRvByYou;
        rvByYou.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(rvByYou, viewModel.getQuizzesOfTopic(topicId));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}