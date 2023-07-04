package pl.kartven.javaproapp.ui.creator.fragment;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.TopicReqApi;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class CreatorTopicViewModel extends ViewModel {
    private final MainRepository mainRepository;

    @Inject
    public CreatorTopicViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<Void> postTopic(String name, String desc) {
        return mainRepository.postTopic(new TopicReqApi(name, desc));
    }
}
