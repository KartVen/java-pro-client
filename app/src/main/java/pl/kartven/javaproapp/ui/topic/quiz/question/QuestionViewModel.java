package pl.kartven.javaproapp.ui.topic.quiz.question;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class QuestionViewModel extends ViewModel {

    private final MainRepository mainRepository;

    @Inject
    public QuestionViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public Resource<List<QuestionDomain>> getQuestionsOfQuiz(Long id) {
        return mainRepository.getQuestionsOfQuiz(id).getValue();
    }
}

