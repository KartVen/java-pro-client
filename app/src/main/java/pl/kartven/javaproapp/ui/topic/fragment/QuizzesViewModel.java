package pl.kartven.javaproapp.ui.topic.fragment;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.SessionManager;

@HiltViewModel
public class QuizzesViewModel extends ViewModel {

    private final MainRepository mainRepository;
    private final SessionManager sessionManager;

    @Inject
    public QuizzesViewModel(MainRepository mainRepository, SessionManager sessionManager) {
        this.mainRepository = mainRepository;
        this.sessionManager = sessionManager;
    }

    public Resource<List<QuizDomain>> getQuizzesOfTopic(Long id) {
        return mainRepository.getQuizzesOfTopic(id, null);
    }

    public Resource<List<QuizDomain>> getMyQuizzesOfTopic(Long id) {
        return mainRepository.getQuizzesOfTopic(id, sessionManager.getUser().getData().getId());
    }
}

