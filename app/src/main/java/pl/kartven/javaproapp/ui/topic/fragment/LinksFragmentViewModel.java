package pl.kartven.javaproapp.ui.topic.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.utils.resource.Resource;

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

    public Resource<List<LinkDomain>> getLinksOfSection(Long id) {
        return mainRepository.getLinksOfSection(id).getValue();
    }
}
