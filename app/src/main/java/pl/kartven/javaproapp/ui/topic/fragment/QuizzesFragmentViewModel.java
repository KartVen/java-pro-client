package pl.kartven.javaproapp.ui.topic.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.utils.resource.Resource;

@HiltViewModel
public class QuizzesFragmentViewModel extends ViewModel {
    private MainRepository mainRepository;

    @Inject
    public QuizzesFragmentViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<QuizDomain>> getQuizzesOfTopic(Long id) {
        return mainRepository.getQuizzesOfTopic(id).getValue();
    }
}
