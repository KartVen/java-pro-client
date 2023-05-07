package pl.kartven.javaproapp.ui.lecture.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import pl.kartven.javaproapp.data.model.SlideApi;
import pl.kartven.javaproapp.ui.lecture.fragment.LectureSlideViewItemFragment;

public class LectureSlideViewAdapter extends FragmentStateAdapter {
    private final List<SlideApi> data;

    public LectureSlideViewAdapter(@NonNull FragmentActivity fragmentActivity, List<SlideApi> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        SlideApi slide = data.get(position);
        return LectureSlideViewItemFragment.newInstance(slide.getId(), slide.getContent());
    }
}
