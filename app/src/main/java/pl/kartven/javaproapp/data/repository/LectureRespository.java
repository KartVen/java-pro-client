package pl.kartven.javaproapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.Mock;
import pl.kartven.javaproapp.data.model.SlideApi;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

public class LectureRespository {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;
    private final MutableLiveData<Resource<List<SlideApi>>> lectureSlidesApiResult = new MutableLiveData<>(null);

    @Inject
    public LectureRespository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }
    public LiveData<Resource<List<SlideApi>>> getLectureSlideList() {
        lectureSlidesApiResult.setValue(
                new Resource.Success<>(
                        Mock.lectureSlideApiList
                )
        );
        return lectureSlidesApiResult;
    }
}
