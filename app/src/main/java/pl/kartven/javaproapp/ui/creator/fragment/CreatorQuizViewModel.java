package pl.kartven.javaproapp.ui.creator.fragment;

import androidx.lifecycle.ViewModel;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.api.request.QuizReqApi;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class CreatorQuizViewModel extends ViewModel {
    private final MainRepository mainRepository;

    @Inject
    public CreatorQuizViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<Void> postQuizOfTopic(Long id, String name, String desc) {
        return mainRepository.postQuizOfTopic(id, new QuizReqApi(name, desc, Collections.emptyList()));
    }
}
