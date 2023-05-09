package pl.kartven.javaproapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.model.SlideApi;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.util.RepositoryUtility;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;
import pl.kartven.javaproapp.util.adapter.CallbackAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LectureRespository extends RepositoryUtility {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;
    private final MutableLiveData<Resource<List<SlideApi>>> lectureSlidesApiResult = new MutableLiveData<>(null);

    @Inject
    public LectureRespository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    public LiveData<Resource<List<SlideApi>>> getLectureSlideList(Long id) {
        try {
            lectureSlidesApiResult.setValue(onResponse(
                    backendApi.getLectureSlides(id, sessionManager.createAuthHeader()).execute())
                    .fold(
                            Resource.Error::new,
                            Resource.Success::new
                    )
            );
        } catch (IOException e) {
            onFailure(lectureSlidesApiResult);
        }
        return lectureSlidesApiResult;
    }
}
