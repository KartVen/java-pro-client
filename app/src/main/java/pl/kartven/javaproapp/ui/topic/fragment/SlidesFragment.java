package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;
import pl.kartven.javaproapp.databinding.FragmentSlidesBinding;
import pl.kartven.javaproapp.ui.topic.TopicViewModel;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SlideListViewPagerAdapter;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseFragment;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;

@AndroidEntryPoint
public class SlidesFragment extends BaseFragment {
    private TopicViewModel topicViewModel;
    private FragmentSlidesBinding binding;
    private SlidesViewModel viewModel;
    private final Executor executor;

    @Inject
    public SlidesFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = getActivityViewModel(TopicViewModel.class);
        viewModel = initViewModel(SlidesViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlidesBinding.inflate(inflater, container, false);
        initViewPager(binding.fSlidesVp);
        return binding.getRoot();
    }

    private void initViewPager(ViewPager viewPager) {

        SlideListViewPagerAdapter adapter = new SlideListViewPagerAdapter(
                requireContext(), new ArrayList<>()
        );

        updateViewPager(adapter);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new SlideListViewPagerAdapter.OnPageChangeListener() {
            @NonNull
            @Override
            public SlideListViewPagerAdapter getAdapter() {
                return adapter;
            }

            @NonNull
            @Override
            public void loadMoreItems() {
                updateViewPager(getAdapter());
            }
        });
    }

    private void updateViewPager(SlideListViewPagerAdapter adapter) {
        executor.execute(() -> {
            Resource<List<SlideDomain>> listResource = viewModel.getSlidesOfTopic(
                    topicViewModel.getTopicDomain().getId(),
                    adapter.getPageAndIncrement(),
                    SlideListViewPagerAdapter.ITEMS_PER_PAGE
            );
            requireActivity().runOnUiThread(() -> {
                adapter.addNewItems(ListUtils.extractList(listResource, requireContext()));
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}