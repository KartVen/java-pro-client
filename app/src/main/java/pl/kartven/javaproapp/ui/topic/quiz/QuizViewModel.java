package pl.kartven.javaproapp.ui.topic.quiz;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class QuizViewModel extends ViewModel {

    private final MainRepository mainRepository;

    @Inject
    public QuizViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<QuizDetailsDomain> getQuizDetails(Long id) {
        return mainRepository.getQuizDetails(id).getValue();
    }
}

