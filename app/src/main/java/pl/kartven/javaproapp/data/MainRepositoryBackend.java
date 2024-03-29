package pl.kartven.javaproapp.data;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.vavr.control.Try;
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
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.ResponseUtils;
import pl.kartven.javaproapp.utils.utility.SessionManager;

public class MainRepositoryBackend extends MainRepository {
    private final BackendApi backendApi;
    private final ExecutorService executor;
    private final SessionManager sessionManager;

    @Inject
    public MainRepositoryBackend(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public Resource<AuthDomain> getAuthData(LoginApi loginApi) {
        Try.of(() -> backendApi.postLogin(loginApi).execute())
                .onFailure(e -> authData = ResponseUtils.onFailure(e))
                .onSuccess(response -> authData = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(AuthDomain.map(data)))
                );
        return authData;
    }

    @Override
    public Resource<AuthDomain> getAuthData(RegisterApi registerDto) {
        Try.of(() -> backendApi.postRegister(registerDto).execute())
                .onFailure(e -> authData = ResponseUtils.onFailure(e))
                .onSuccess(response -> authData = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(AuthDomain.map(data)))
                );
        return authData;
    }

    @Override
    public Resource<List<TopicDomain>> getTopics() {
        Try.of(() -> backendApi.getTopics().execute())
                .onFailure(e -> topics = ResponseUtils.onFailure(e))
                .onSuccess(response -> topics = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(TopicDomain.map(data)))
                );
        return topics;
    }

    @Override
    public Resource<List<TopicDomain>> getMyTopics() {
        Try.of(() -> backendApi.getMyTopics(sessionManager.getUser().getData().getId()).execute())
                .onFailure(e -> myTopics = ResponseUtils.onFailure(e))
                .onSuccess(response -> myTopics = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(TopicDomain.map(data)))
                );
        return myTopics;
    }

    @Override
    public Resource<List<SectionDomain>> getSectionsOfTopic(Long id) {
        Try.of(() -> backendApi.getSectionsOfTopic(id).execute())
                .onFailure(e -> sectionsOfTopics = ResponseUtils.onFailure(e))
                .onSuccess(response -> sectionsOfTopics = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(SectionDomain.map(data)))
                );
        return sectionsOfTopics;
    }

    @Override
    public Resource<List<QuizDomain>> getQuizzesOfTopic(Long id, Long userId) {
        Try.of(() -> backendApi.getQuizzesOfTopic(id, userId).execute())
                .onFailure(e -> quizzesOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> quizzesOfTopic =
                        ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, data -> new Resource.Success<>(QuizDomain.map(data)))
                );
        return quizzesOfTopic;
    }

    @Override
    public Resource<List<CodeDomain>> getCodesOfSection(Long id) {
        Try.of(() -> backendApi.getCodesOfSection(id).execute())
                .onFailure(e -> codesOfSection = ResponseUtils.onFailure(e))
                .onSuccess(response -> codesOfSection = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(CodeDomain.map(data)))
                );
        return codesOfSection;
    }

    @Override
    public Resource<List<QuestionDomain>> getQuestionsOfQuiz(Long id) {
        Try.of(() -> backendApi.getQuestionsOfQuiz(sessionManager.getBearerToken(), id).execute())
                .onFailure(e -> questionsOfQuiz = ResponseUtils.onFailure(e))
                .onSuccess(response -> questionsOfQuiz = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(QuestionDomain.map(data)))
                );
        return questionsOfQuiz;
    }

    @Override
    public Resource<QuizDetailsDomain> getQuizDetails(Long id) {
        Try.of(() -> backendApi.getQuizDetails(id).execute())
                .onFailure(e -> quizDetails = ResponseUtils.onFailure(e))
                .onSuccess(response -> quizDetails = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(QuizDetailsDomain.map(data)))
                );
        return quizDetails;
    }

    @Override
    public Resource<List<LinkDomain>> getLinksOfSection(Long id) {
        Try.of(() -> backendApi.getLinksOfSection(id).execute())
                .onFailure(e -> linksOfSection = ResponseUtils.onFailure(e))
                .onSuccess(response -> linksOfSection = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(LinkDomain.map(data)))
                );
        return linksOfSection;
    }

    @Override
    public Resource<List<SlideDomain>> getSlidesOfTopic(Long id, Integer page, Integer size) {
        Try.of(() -> backendApi.getSlidesOfTopic(id, page, size).execute())
                .onFailure(e -> slidesOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> slidesOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(SlideDomain.map(data)))
                );
        return slidesOfTopic;
    }

    @Override
    public Resource<TopicDomain> postTopic(TopicReqApi topicReqApi) {
        Try.of(() -> backendApi.postTopic(sessionManager.getBearerToken(), topicReqApi).execute())
                .onFailure(e -> postTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> postTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(TopicDomain.map(data)))
                );
        return postTopic;
    }

    @Override
    public Resource<Void> postSlidesOfTopic(Long id, List<MultipartBody.Part> slides) {
        Try.of(() -> backendApi.postSlidesOfTopic(sessionManager.getBearerToken(), id, slides).execute())
                .onFailure(e -> postSlidesOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> postSlidesOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(null))
                );
        return postSlidesOfTopic;
    }

    @Override
    public Resource<Void> postCodeOfTopic(Long id, CodeReqApi codeReqApi) {
        Try.of(() -> backendApi.postCodeOfTopic(sessionManager.getBearerToken(), id, codeReqApi).execute())
                .onFailure(e -> postCodeOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> postCodeOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(null))
                );
        return postCodeOfTopic;
    }

    @Override
    public Resource<Void> postLinkOfTopic(Long id, LinkReqApi linkReqApi) {
        Try.of(() -> backendApi.postLinkOfTopic(sessionManager.getBearerToken(), id, linkReqApi).execute())
                .onFailure(e -> postLinkOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> postLinkOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(null))
                );
        return postLinkOfTopic;
    }

    @Override
    public Resource<Void> postQuizOfTopic(Long id, QuizReqApi quizReqApi) {
        Try.of(() -> backendApi.postQuizOfTopic(sessionManager.getBearerToken(), id, quizReqApi).execute())
                .onFailure(e -> postQuizOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> postQuizOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, data -> new Resource.Success<>(null))
                );
        return postQuizOfTopic;
    }
}
