package pl.kartven.javaproapp.ui.laboratory;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.repository.RangeRepository;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.ui.model.Range;
import pl.kartven.javaproapp.util.Resource;

@HiltViewModel
public class LaboratoryRangeSelectionViewModel extends ViewModel {

    private RangeRepository rangeRepository;

    @Inject
    public LaboratoryRangeSelectionViewModel(RangeRepository rangeRepository){
        this.rangeRepository = rangeRepository;
    }

    public Resource<List<LectureListItemDetails>> getRangeData() {
        return rangeRepository.getLaboratoryRangeList().getValue();
    }
}
