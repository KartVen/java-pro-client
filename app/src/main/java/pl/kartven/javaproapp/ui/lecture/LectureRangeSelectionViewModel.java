package pl.kartven.javaproapp.ui.lecture;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.repository.RangeRepository;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.ui.model.Range;
import pl.kartven.javaproapp.util.Resource;

@HiltViewModel
public class LectureRangeSelectionViewModel extends ViewModel {

    private RangeRepository rangeRepository;

    @Inject
    public LectureRangeSelectionViewModel(RangeRepository rangeRepository){
        this.rangeRepository = rangeRepository;
    }

    public Resource<List<LectureListItemDetails>> getRangeData() {
        return rangeRepository.getLectureRangeList().getValue();
    }
}
