package pl.kartven.javaproapp.data;

import java.util.List;

import okhttp3.MultipartBody;
import pl.kartven.javaproapp.data.model.api.request.CodeReqApi;
import pl.kartven.javaproapp.data.model.api.request.LinkReqApi;
import pl.kartven.javaproapp.data.model.api.request.LoginApi;
import pl.kartven.javaproapp.data.model.api.request.QuizReqApi;
import pl.kartven.javaproapp.data.model.api.request.RegisterApi;
import pl.kartven.javaproapp.data.model.api.request.TopicReqApi;
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
    protected Resource<TopicDomain> postTopic = new Resource.Error<>(INIT_ERROR);
    protected Resource<Void> postSlidesOfTopic = new Resource.Error<>(INIT_ERROR);
    protected Resource<Void> postCodeOfTopic = new Resource.Error<>(INIT_ERROR);
    protected Resource<Void> postLinkOfTopic = new Resource.Error<>(INIT_ERROR);
    protected Resource<Void> postQuizOfTopic = new Resource.Error<>(INIT_ERROR);


    public abstract Resource<AuthDomain> getAuthData(LoginApi loginApi);

    public abstract Resource<AuthDomain> getAuthData(RegisterApi registerDto);

    public abstract Resource<List<TopicDomain>> getTopics();

    public abstract Resource<List<TopicDomain>> getMyTopics();

    public abstract Resource<List<SectionDomain>> getSectionsOfTopic(Long id);

    public abstract Resource<List<QuizDomain>> getQuizzesOfTopic(Long id, Long userId);

    public abstract Resource<List<CodeDomain>> getCodesOfSection(Long id);

    public abstract Resource<List<QuestionDomain>> getQuestionsOfQuiz(Long id);

    public abstract Resource<QuizDetailsDomain> getQuizDetails(Long id);

    public abstract Resource<List<LinkDomain>> getLinksOfSection(Long id);

    public abstract Resource<List<SlideDomain>> getSlidesOfTopic(Long topicId, Integer page, Integer size);

    public abstract Resource<TopicDomain> postTopic(TopicReqApi topicReqApi);

    public abstract Resource<Void> postSlidesOfTopic(Long id, List<MultipartBody.Part> slides);

    public abstract Resource<Void> postCodeOfTopic(Long id, CodeReqApi codeReqApi);

    public abstract Resource<Void> postLinkOfTopic(Long id, LinkReqApi linkReqApi);

    public abstract Resource<Void> postQuizOfTopic(Long id, QuizReqApi quizReqApi);
}
