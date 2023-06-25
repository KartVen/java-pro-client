package pl.kartven.javaproapp.ui.main;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.resource.Resource;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MainRepository mainRepository;

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<TopicDomain>> getTopics() {
        return mainRepository.getTopics().getValue();
    }

    public Resource<List<TopicDomain>> getMyTopics() {
        return mainRepository.getMyTopics().getValue();
    }
}

