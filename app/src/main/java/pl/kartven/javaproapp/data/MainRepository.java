package pl.kartven.javaproapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.net.HttpCookie;
import java.util.List;

import javax.inject.Inject;

import io.vavr.control.Try;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.util.RepositoryUtility;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

public class MainRepository extends RepositoryUtility {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;

    @Inject
    public MainRepository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    private final MutableLiveData<Resource<List<TopicDomain>>> topics = new MutableLiveData<>(null);

    public LiveData<Resource<List<TopicDomain>>> getTopics() {
        Try.of(() -> backendApi.getTopics().execute())
                .onFailure(e -> onFailure(topics))
                .onSuccess(response -> topics
                        .setValue(onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(TopicDomain.map(list)))
                        ));
        //topics.setValue(new Resource.Success<>(Mock.getTopics()));
        return topics;
    }

    private final MutableLiveData<Resource<List<SectionDomain>>> sections = new MutableLiveData<>(null);

    public LiveData<Resource<List<SectionDomain>>> getSectionsOfTopic(Long id) {
        Try.of(() -> backendApi.getSectionsOfTopic(id).execute())
                .onFailure(e -> onFailure(sections))
                .onSuccess(response -> sections
                        .setValue(onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(SectionDomain.map(list)))
                        ));
        return sections;
    }

    private final MutableLiveData<Resource<List<QuizDomain>>> quizzes = new MutableLiveData<>(null);

    public LiveData<Resource<List<QuizDomain>>> getQuizzesOfTopic(Long id) {
        Try.of(() -> backendApi.getQuizzesOfTopic(id).execute())
                .onFailure(e -> onFailure(quizzes))
                .onSuccess(response -> quizzes
                        .setValue(onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(QuizDomain.map(list)))
                        ));
        return quizzes;
    }

    private final MutableLiveData<Resource<List<CodeDomain>>> codes = new MutableLiveData<>(null);

    public LiveData<Resource<List<CodeDomain>>> getCodesOfSection(Long id) {
        Try.of(() -> backendApi.getCodesOfSection(id).execute())
                .onFailure(e -> onFailure(codes))
                .onSuccess(response -> codes
                        .setValue(onResponse(response)
                                .fold(Resource.Error::new, list -> new Resource.Success<>(CodeDomain.map(list)))
                        ));
        return codes;
    }
}
