package pl.kartven.javaproapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import pl.kartven.javaproapp.data.Mock;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.ui.model.Range;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;

public class RangeRepository {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;
    private final MutableLiveData<Resource<List<LectureListItemDetails>>> lectureRangeListResult = new MutableLiveData<>(null);
    private final MutableLiveData<Resource<List<LectureListItemDetails>>> laboratoryRangeListResult = new MutableLiveData<>(null);

    @Inject
    public RangeRepository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    public LiveData<Resource<List<LectureListItemDetails>>> getLectureRangeList() {
        lectureRangeListResult.setValue(
                new Resource.Success<>(
                        LectureListItemDetails.map(Mock.lectureRangeApiDetailsList)
                )
        );
        return lectureRangeListResult;
    }

    public LiveData<Resource<List<LectureListItemDetails>>> getLaboratoryRangeList() {
        laboratoryRangeListResult.setValue(
                new Resource.Success<>(
                        LectureListItemDetails.map(Mock.laboratoryRangeApiDetailsList)
                )
        );
        return laboratoryRangeListResult;
    }
}
