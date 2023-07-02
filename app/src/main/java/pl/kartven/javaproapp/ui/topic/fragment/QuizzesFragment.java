package pl.kartven.javaproapp.ui.topic.fragment;

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
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.databinding.FragmentQuizzesBinding;
import pl.kartven.javaproapp.ui.topic.TopicViewModel;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.QuizListAdapter;
import pl.kartven.javaproapp.ui.topic.quiz.QuizActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class QuizzesFragment extends BaseFragment {
    private TopicViewModel topicViewModel;
    private FragmentQuizzesBinding binding;
    private QuizzesViewModel viewModel;

    @Inject
    public QuizzesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = getActivityViewModel(TopicViewModel.class);
        viewModel = initViewModel(QuizzesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false);
        initRecyclerView(binding.fQuizzesRvBase, () -> viewModel.getQuizzesOfTopic(topicViewModel.getTopicDomain().getId()));
        initRecyclerView(binding.fQuizzesRvByYou, () -> viewModel.getQuizzesOfTopic(topicViewModel.getTopicDomain().getId()));
        return binding.getRoot();
    }

    private void initRecyclerView(RecyclerView recyclerView, Supplier<Resource<List<QuizDomain>>> resourceSupplier) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        QuizListAdapter adapter = new QuizListAdapter(new ArrayList<>());

        Executors.newSingleThreadExecutor().execute(() -> {
            Resource<List<QuizDomain>> listResource = resourceSupplier.get();
            requireActivity().runOnUiThread(() -> adapter.updateList(ListUtils.extractList(listResource, requireContext())));
        });

        adapter.setItemClicked((model, position) -> {
            ActivityUtils.goToActivity(requireContext(), QuizActivity.class, intent -> {
                intent.putExtra(Constant.Extra.QUIZ_MODEL, model);
            });
            requireActivity().finish();
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}