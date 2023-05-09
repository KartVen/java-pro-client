package pl.kartven.javaproapp.ui.lecture;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.vavr.control.Option;
import pl.kartven.javaproapp.data.model.SlideApi;
import pl.kartven.javaproapp.databinding.ActivityLectureSlideViewBinding;
import pl.kartven.javaproapp.ui.lecture.adapter.LectureSlideViewAdapter;
import pl.kartven.javaproapp.util.Extra;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.ActivityUtility;

@AndroidEntryPoint
public class LectureSlideViewActivity extends AppCompatActivity implements ActivityUtility {

    private ActivityLectureSlideViewBinding binding;
    private LectureSlideViewViewModel viewModel;
    private Long id;
    private String topic;

    @Inject
    public LectureSlideViewActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLectureSlideViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(LectureSlideViewViewModel.class);

        Option.of(savedInstanceState)
                .map(bundle -> {
                    id = bundle.getLong(Extra.LECTURE_ID);
                    topic = bundle.getString(Extra.LECTURE_NAME);
                    return true;
                })
                .getOrElse(Option.of(getIntent().getExtras())
                        .map(bundle -> {
                            id = bundle.getLong(Extra.LECTURE_ID);
                            topic = bundle.getString(Extra.LECTURE_NAME);
                            return true;
                        })
                        .getOrElse(false)
                );

        setSupportActionBar((Toolbar) binding.activityAppBar.getChildAt(0));
        Option.of(getSupportActionBar())
                .map(actionBar -> {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setTitle(topic);
                    return null;
                });
        initViewPager();
    }

    private void initViewPager() {
        Resource<List<SlideApi>> rangeData = viewModel.getLectureSlide(id);
        LectureSlideViewAdapter adapter;
        if (rangeData.isSuccess()) {
            adapter = new LectureSlideViewAdapter(this, rangeData.getData());
        } else {
            showToast(this, rangeData.getMessage());
            adapter = new LectureSlideViewAdapter(this, Collections.emptyList());
        }

        ViewPager2 viewPager = binding.lectureViewVpElement;
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        ViewPager2 viewPager = binding.lectureViewVpElement;
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}