package pl.kartven.javaproapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.resource.Resource;

public abstract class MainRepository {
    protected final MutableLiveData<Resource<List<TopicDomain>>> topics = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<TopicDomain>>> myTopics = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<SectionDomain>>> sectionsOfTopics = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<QuizDomain>>> quizzesOfTopic = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<CodeDomain>>> codesOfSection = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<QuestionDomain>>> questionsOfQuiz = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<QuizDetailsDomain>> quizDetails = new MutableLiveData<>(new Resource.Error<>(""));
    protected final MutableLiveData<Resource<List<LinkDomain>>> linksOfSection = new MutableLiveData<>(new Resource.Error<>(""));

    public abstract LiveData<Resource<List<TopicDomain>>> getTopics();

    public abstract LiveData<Resource<List<TopicDomain>>> getMyTopics();

    public abstract LiveData<Resource<List<SectionDomain>>> getSectionsOfTopic(Long id);

    public abstract LiveData<Resource<List<QuizDomain>>> getQuizzesOfTopic(Long id);

    public abstract LiveData<Resource<List<CodeDomain>>> getCodesOfSection(Long id);

    public abstract LiveData<Resource<List<QuestionDomain>>> getQuestionsOfQuiz(Long id);

    public abstract LiveData<Resource<QuizDetailsDomain>> getQuizDetails(Long id);

    public abstract LiveData<Resource<List<LinkDomain>>> getLinksOfSection(Long id);
}
