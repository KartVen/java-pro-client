package pl.kartven.javaproapp.util;

import androidx.lifecycle.MutableLiveData;

import io.vavr.control.Either;
import retrofit2.Response;

public abstract class RepositoryUtility {
    protected <S> void onFailure(MutableLiveData<Resource<S>> liveData) {
        liveData.setValue(new Resource.Error<S>("Couldn't reach server", null));
    }

    protected <S> Resource<S> onFailure(Resource<S> resourceData) {
        return new Resource.Error<S>("Couldn't reach server", null);
    }

    protected <T> Either<String, T> onResponse(Response<T> response) {
        return response.isSuccessful() && response.body() != null ?
                Either.right(response.body()) :
                Either.left(response.message());
    }
}
