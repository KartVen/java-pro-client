package pl.kartven.javaproapp.ui.topic.quiz.question;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.kartven.javaproapp.data.MainRepository;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.utils.utility.Resource;

@HiltViewModel
public class QuestionViewModel extends ViewModel {

    private final MainRepository mainRepository;
    private QuizDomain quizDomain;
    private List<QuestionDomain> questionsList;

    @Inject
    public QuestionViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public QuizDomain getQuizDomain() {
        return quizDomain;
    }

    public void setQuizDomain(QuizDomain quizDomain) {
        this.quizDomain = quizDomain;
    }

    public Resource<List<QuestionDomain>> getQuestionsOfTopic(Long id){
        return mainRepository.getQuestionsOfQuiz(id);
    }

    public void setQuestionsList(List<QuestionDomain> questionsList) {
        this.questionsList = questionsList;
    }

    public List<QuestionDomain> getQuestionsList() {
        return questionsList;
    }
}

