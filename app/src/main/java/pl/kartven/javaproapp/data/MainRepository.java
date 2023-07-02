package pl.kartven.javaproapp.data;

import java.util.List;

import pl.kartven.javaproapp.data.model.api.request.LoginDto;
import pl.kartven.javaproapp.data.model.api.request.RegisterDto;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.utility.Constant;
import pl.kartven.javaproapp.utils.utility.Resource;

public abstract class MainRepository {
    private final static String INIT_ERROR = Constant.Expression.INIT_ERROR;
    protected Resource<AuthDomain> authData = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<TopicDomain>> topics = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<TopicDomain>> myTopics = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<SectionDomain>> sectionsOfTopics = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<QuizDomain>> quizzesOfTopic = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<CodeDomain>> codesOfSection = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<QuestionDomain>> questionsOfQuiz = new Resource.Error<>(INIT_ERROR);
    protected Resource<QuizDetailsDomain> quizDetails = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<LinkDomain>> linksOfSection = new Resource.Error<>(INIT_ERROR);
    protected Resource<List<SlideDomain>> slidesOfTopic = new Resource.Error<>(INIT_ERROR);

    public abstract Resource<AuthDomain> getAuthData(LoginDto loginDto);
    public abstract Resource<AuthDomain> getAuthData(RegisterDto registerDto);

    public abstract Resource<List<TopicDomain>> getTopics();

    public abstract Resource<List<TopicDomain>> getMyTopics();

    public abstract Resource<List<SectionDomain>> getSectionsOfTopic(Long id);

    public abstract Resource<List<QuizDomain>> getQuizzesOfTopic(Long id);

    public abstract Resource<List<CodeDomain>> getCodesOfSection(Long id);

    public abstract Resource<List<QuestionDomain>> getQuestionsOfQuiz(Long id);

    public abstract Resource<QuizDetailsDomain> getQuizDetails(Long id);

    public abstract Resource<List<LinkDomain>> getLinksOfSection(Long id);

    public abstract Resource<List<SlideDomain>> getSlidesOfTopic(Long topicId, Integer page, Integer size);
}
