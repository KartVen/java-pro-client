package pl.kartven.javaproapp.ui.topic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pl.kartven.javaproapp.R;
import pl.kartven.javaproapp.databinding.FragmentSlidesBinding;
import pl.kartven.javaproapp.ui.topic.fragment.adapter.SlideListViewPagerAdapter;

@AndroidEntryPoint
public class SlidesFragment extends Fragment {

    private FragmentSlidesBinding binding;
    private int[] images = {
            R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo, R.drawable.java_logo
    };

    @Inject
    public SlidesFragment() {
        // Required empty public constructor
    }

    public static SlidesFragment newInstance() {
        return new SlidesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlidesBinding.inflate(inflater, container, false);

        ViewPager viewPager = binding.fragmentSlidesVp;
        viewPager.setAdapter(
                new SlideListViewPagerAdapter(requireActivity(), images)
        );

        return binding.getRoot();
    }
}
