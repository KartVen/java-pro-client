package pl.kartven.javaproapp.ui.lecture;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.model.SlideApi;
import pl.kartven.javaproapp.data.repository.LectureRespository;
import pl.kartven.javaproapp.util.Resource;

@HiltViewModel
public class LectureSlideViewViewModel extends ViewModel {
    private final LectureRespository lectureRespository;

    @Inject
    public LectureSlideViewViewModel(LectureRespository lectureRespository) {
        this.lectureRespository = lectureRespository;
    }

    public Resource<List<SlideApi>> getLectureSlide(Long id) {
        return lectureRespository.getLectureSlideList(id).getValue();
    }
}