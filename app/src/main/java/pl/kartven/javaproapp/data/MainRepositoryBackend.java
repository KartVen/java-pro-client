package pl.kartven.javaproapp.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.vavr.control.Try;
import pl.kartven.javaproapp.data.model.api.AuthDto;
import pl.kartven.javaproapp.data.model.api.request.LoginDto;
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
    private final SessionManager sessionManager;

    @Inject
    public MainRepositoryBackend(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    @Override
    public LiveData<Resource<AuthDto>> getAuthData(LoginDto loginDto) {
        authData.setValue(new Resource.Success<>(new AuthDto(
                "kartven",
                "krystian.kielbasa@hotmail.com",
                "",
                ""
        )));
        return authData;
    }

    @Override
    public LiveData<Resource<List<TopicDomain>>> getTopics() {
        Try.of(() -> backendApi.getTopics().execute())
                .onFailure(e -> ResponseUtils.onFailure(topics, e))
                .onSuccess(response -> topics
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(TopicDomain.map(list)))
                        ));
        return topics;
    }

    @Override
    public LiveData<Resource<List<TopicDomain>>> getMyTopics() {
        Try.of(() -> backendApi.getTopics().execute())
                .onFailure(e -> ResponseUtils.onFailure(myTopics, e))
                .onSuccess(response -> myTopics
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(TopicDomain.map(list)))
                        ));
        return myTopics;
    }

    @Override
    public LiveData<Resource<List<SectionDomain>>> getSectionsOfTopic(Long id) {
        Try.of(() -> backendApi.getSectionsOfTopic(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(sectionsOfTopics, e))
                .onSuccess(response -> sectionsOfTopics
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(SectionDomain.map(list)))
                        ));
        return sectionsOfTopics;
    }

    @Override
    public LiveData<Resource<List<QuizDomain>>> getQuizzesOfTopic(Long id) {
        Try.of(() -> backendApi.getQuizzesOfTopic(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(quizzesOfTopic, e))
                .onSuccess(response -> quizzesOfTopic
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(QuizDomain.map(list)))
                        ));
        return quizzesOfTopic;
    }

    @Override
    public LiveData<Resource<List<CodeDomain>>> getCodesOfSection(Long id) {
        Try.of(() -> backendApi.getCodesOfSection(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(codesOfSection, e))
                .onSuccess(response -> codesOfSection
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(CodeDomain.map(list)))
                        ));
        return codesOfSection;
    }

    @Override
    public LiveData<Resource<List<QuestionDomain>>> getQuestionsOfQuiz(Long id) {
        Try.of(() -> backendApi.getQuestionsOfQuiz(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(questionsOfQuiz, e))
                .onSuccess(response -> questionsOfQuiz
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(QuestionDomain.map(list)))
                        ));
        return questionsOfQuiz;
    }

    @Override
    public LiveData<Resource<QuizDetailsDomain>> getQuizDetails(Long id) {
        Try.of(() -> backendApi.getQuizDetails(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(quizDetails, e))
                .onSuccess(response -> quizDetails
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, item -> new Resource.Success<>(QuizDetailsDomain.map(item)))
                        ));
        return quizDetails;
    }

    @Override
    public LiveData<Resource<List<LinkDomain>>> getLinksOfSection(Long id) {
        Try.of(() -> backendApi.getLinksOfSection(id).execute())
                .onFailure(e -> ResponseUtils.onFailure(linksOfSection, e))
                .onSuccess(response -> linksOfSection
                        .setValue(ResponseUtils.onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(LinkDomain.map(list)))
                        ));
        return linksOfSection;
    }

    @Override
    public Resource<List<SlideDomain>> getSlidesOfTopic(Long id, Integer page, Integer size) {
        Try.of(() -> backendApi.getSlidesOfTopic(id, page, size).execute())
                .onFailure(e -> slidesOfTopic = ResponseUtils.onFailure(e))
                .onSuccess(response -> slidesOfTopic = ResponseUtils.onResponse(response)
                        .fold(Resource.Error::new, list -> new Resource.Success<>(SlideDomain.map(list)))
                );
        return slidesOfTopic;
    }
}
