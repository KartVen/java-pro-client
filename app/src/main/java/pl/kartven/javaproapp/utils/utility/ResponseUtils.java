package pl.kartven.javaproapp.utils.utility;

import androidx.lifecycle.MutableLiveData;

import io.vavr.control.Either;
import pl.kartven.javaproapp.utils.resource.Resource;
import retrofit2.Response;

public class ResponseUtils {
    private ResponseUtils() {
    }

    public static <S> void onFailure(MutableLiveData<Resource<S>> liveData) {
        liveData.setValue(new Resource.Error<S>(Constant.Expression.SERVER_CONNECTION_ERROR, null));
    }

    public static <S> Resource<S> onFailure(Resource<S> resourceData) {
        return new Resource.Error<S>(Constant.Expression.SERVER_CONNECTION_ERROR, null);
    }

    public static <T> Either<String, T> onResponse(Response<T> response) {
        return response.isSuccessful() && response.body() != null ?
                Either.right(response.body()) :
                Either.left(response.message());
    }
}
