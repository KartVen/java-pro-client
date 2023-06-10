package pl.kartven.javaproapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.vavr.control.Either;
import io.vavr.control.Option;
import pl.kartven.javaproapp.data.Mock;
import pl.kartven.javaproapp.data.model.SingleRangeApiDetails;
import pl.kartven.javaproapp.data.remote.BackendApi;
import pl.kartven.javaproapp.ui.model.FieldState;
import pl.kartven.javaproapp.ui.model.LectureListItemDetails;
import pl.kartven.javaproapp.util.RepositoryUtility;
import pl.kartven.javaproapp.util.Resource;
import pl.kartven.javaproapp.util.SessionManager;
import pl.kartven.javaproapp.util.adapter.CallbackAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RangeRepository extends RepositoryUtility {
    private final BackendApi backendApi;
    private final SessionManager sessionManager;
    private final MutableLiveData<Resource<List<LectureListItemDetails>>> lectureRangeListResult = new MutableLiveData<>(null);
    private final MutableLiveData<Resource<List<LectureListItemDetails>>> laboratoryRangeListResult = new MutableLiveData<>(null);
    private final MutableLiveData<Resource<List<LectureListItemDetails>>> quizRangeListResult = new MutableLiveData<>(null);

    @Inject
    public RangeRepository(BackendApi backendApi, SessionManager sessionManager) {
        this.backendApi = backendApi;
        this.sessionManager = sessionManager;
    }

    public LiveData<Resource<List<LectureListItemDetails>>> getLectureRangeList() {
        try {
            lectureRangeListResult.setValue(onResponse(
                    backendApi.getRangeList(sessionManager.createAuthHeader()).execute())
                    .fold(
                            Resource.Error::new,
                            singleRangeApiDetails -> new Resource.Success<>(LectureListItemDetails.map(singleRangeApiDetails))
                    )
            );
        } catch (IOException e) {
            onFailure(lectureRangeListResult);
        }
        return lectureRangeListResult;
    }

    public LiveData<Resource<List<LectureListItemDetails>>> getLaboratoryRangeList() {
        try {
            laboratoryRangeListResult.setValue(onResponse(
                    backendApi.getRangeList(sessionManager.createAuthHeader()).execute())
                    .fold(
                            Resource.Error::new,
                            singleRangeApiDetails -> new Resource.Success<>(LectureListItemDetails.map(singleRangeApiDetails))
                    )
            );
        } catch (IOException e) {
            onFailure(laboratoryRangeListResult);
        }
        return laboratoryRangeListResult;
    }

    public LiveData<Resource<List<LectureListItemDetails>>> getQuizRangeList() {
        try {
            quizRangeListResult.setValue(onResponse(
                    backendApi.getRangeList(sessionManager.createAuthHeader()).execute())
                    .fold(
                            Resource.Error::new,
                            singleRangeApiDetails -> new Resource.Success<>(LectureListItemDetails.map(singleRangeApiDetails))
                    )
            );
        } catch (IOException e) {
            onFailure(quizRangeListResult);
        }
        return quizRangeListResult;
    }
}
