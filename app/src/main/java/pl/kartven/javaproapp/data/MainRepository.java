package pl.kartven.javaproapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import io.vavr.control.Try;
import pl.kartven.javaproapp.data.model.api.TopicApi;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.util.RepositoryUtility;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

public class MainRepository extends RepositoryUtility {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;
    private final MutableLiveData<Resource<List<TopicDomain>>> topics = new MutableLiveData<>(null);

    @Inject
    public MainRepository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    public LiveData<Resource<List<TopicDomain>>> getTopics() {
        /*Try.of(() -> backendApi.getTopics().execute())
                .onFailure(e -> onFailure(topics))
                .onSuccess(response -> topics
                        .setValue(onResponse(response)
                        .fold(Resource.Error::new, list -> new Resource.Success<>(TopicDomain.map(list)))
                ));*/
        topics.setValue(new Resource.Success<>(Mock.getTopics()));
        return topics;
    }
}
