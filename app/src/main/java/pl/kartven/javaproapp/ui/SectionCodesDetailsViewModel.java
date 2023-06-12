package pl.kartven.javaproapp.ui;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.util.Resource;

@HiltViewModel
public class SectionCodesDetailsViewModel extends ViewModel {
    private final MainRepository mainRepository;

    @Inject
    public SectionCodesDetailsViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<CodeDomain>> getCodesOfSection(Long id) {
        return mainRepository.getCodesOfSection(id).getValue();
    }
}
