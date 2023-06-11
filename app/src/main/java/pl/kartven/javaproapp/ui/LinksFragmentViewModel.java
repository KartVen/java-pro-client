package pl.kartven.javaproapp.ui;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.util.Resource;

@HiltViewModel
public class LinksFragmentViewModel extends ViewModel {
    private MainRepository mainRepository;

    @Inject
    public LinksFragmentViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<SectionDomain>> getSectionsOfTopic(Long id) {
        return mainRepository.getSectionsOfTopic(id).getValue();
    }
}
