package pl.kartven.javaproapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.vavr.control.Try;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.databinding.FragmentSlidesBinding;
import pl.kartven.javaproapp.ui.adapter.SlideListViewPagerAdapter;
import pl.kartven.javaproapp.util.Constants;

public class SlidesFragment extends Fragment {

    private FragmentSlidesBinding binding;
    private TopicDomain topic;
    private int[] images = {
            R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo
    };

    public SlidesFragment() {
        // Required empty public constructor
    }

    public static SlidesFragment newInstance(TopicDomain topic) {
        SlidesFragment fragment = new SlidesFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlidesBinding.inflate(inflater, container, false);

        ViewPager viewPager = binding.slidesFragmentViewPager1;
        viewPager.setAdapter(
                new SlideListViewPagerAdapter(requireActivity(), images)
        );

        return binding.getRoot();
    }
}
