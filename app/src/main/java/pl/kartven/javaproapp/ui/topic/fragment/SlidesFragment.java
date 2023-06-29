package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentSlidesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SlideListViewPagerAdapter;
import pl.kartven.javaproapp.utils.utility.ActivityUtils;
import pl.kartven.javaproapp.utils.utility.BaseActivity;
import pl.kartven.javaproapp.utils.utility.ListUtils;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.State;

@AndroidEntryPoint
public class SlidesFragment extends Fragment {

    private FragmentSlidesBinding binding;
    private SlidesFragmentViewModel viewModel;
    private final Executor executor;

    private List<Integer> images = Arrays.stream(new int[]{
            R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo
    }).boxed().collect(Collectors.toList());

    @Inject
    public SlidesFragment() {
        // Required empty public constructor
        executor = Executors.newSingleThreadExecutor();
    }

    public static SlidesFragment newInstance() {
        return new SlidesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = BaseActivity.initViewModel(this, SlidesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlidesBinding.inflate(inflater, container, false);

        initViewPager();

        return binding.getRoot();
    }

    private void initViewPager() {
        Long topicId = State.<TopicDomain>getState().getData().getId();

        ViewPager viewPager = binding.fragmentSlidesVp;

        SlideListViewPagerAdapter adapter = new SlideListViewPagerAdapter(
                requireContext(), new ArrayList<>()
        );
        viewPager.setAdapter(adapter);

        executor.execute(() -> {
            Resource<List<SlideDomain>> slidesOfTopic = viewModel.getSlidesOfTopic(topicId, adapter.getPage(), SlideListViewPagerAdapter.ITEMS_PER_PAGE);
            List<SlideDomain> slides = ListUtils.extractList(slidesOfTopic);
            requireActivity().runOnUiThread(() -> adapter.addNewItems(slides));
        });

        viewPager.addOnPageChangeListener(new SlideListViewPagerAdapter.OnPageChangeListener() {
            @NonNull
            @Override
            public SlideListViewPagerAdapter getAdapter() {
                return adapter;
            }

            @NonNull
            @Override
            public List<SlideDomain> loadMoreItems() {
                Resource<List<SlideDomain>> moreSlidesOfTopic = viewModel.getSlidesOfTopic(
                        topicId,
                        adapter.getPage(),
                        SlideListViewPagerAdapter.ITEMS_PER_PAGE
                );
                return ListUtils.extractList(moreSlidesOfTopic);
            }
        });
    }
}
