package pl.kartven.javaproapp.ui.topic.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class SlidesViewModel extends ViewModel {

    private final MainRepository mainRepository;

    @Inject
    public SlidesViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<SlideDomain>> getSlidesOfTopic(Long topicId, Integer page, Integer size) {
        return mainRepository.getSlidesOfTopic(topicId, page, size);
    }

}

