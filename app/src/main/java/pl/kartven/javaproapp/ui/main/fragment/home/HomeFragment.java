package pl.kartven.javaproapp.ui.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentHomeBinding;
import pl.kartven.javaproapp.ui.main.fragment.home.adapter.TopicListAdapter;
import pl.kartven.javaproapp.ui.topic.TopicActivity;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Inject
    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel(HomeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initContent();
        return binding.getRoot();
    }

    @Override
    protected void initContent() {
        super.initContent();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rvTopicBase = binding.fHomeRvTopicBase;
        rvTopicBase.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(rvTopicBase, viewModel.getTopics());

        RecyclerView rvTopicByYou = binding.fHomeRvTopicByYou;
        rvTopicByYou.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );
        setAdapter(rvTopicByYou, viewModel.getMyTopics());
    }

    private void setAdapter(RecyclerView recyclerView, Resource<List<TopicDomain>> topics) {
        TopicListAdapter adapter = new TopicListAdapter(ListUtils.extractList(topics, requireContext()));
        adapter.setItemClicked((model, position) -> ActivityUtils.goToActivity(
                requireContext(),
                TopicActivity.class,
                intent -> intent.putExtra(Constant.Extra.TOPIC_MODEL, model)
        ));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}