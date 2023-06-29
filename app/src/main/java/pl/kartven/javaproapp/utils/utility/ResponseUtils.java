package pl.kartven.javaproapp.utils.utility;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.vavr.control.Either;
import retrofit2.Response;

public class ResponseUtils {
    private ResponseUtils() {
    }

    public static <S> void onFailure(MutableLiveData<Resource<S>> liveData, Throwable e) {
        Log.e("ApiRequest", "onFailure", e);
        liveData.setValue(new Resource.Error<S>(Constant.Expression.SERVER_CONNECTION_ERROR, null));
    }

    public static <S> Resource.Error<S> onFailure(Throwable e) {
        Log.e("ApiRequest", "onFailure", e);
        return new Resource.Error<S>(Constant.Expression.SERVER_CONNECTION_ERROR, null);
    }

    public static <T> Either<String, T> onResponse(Response<T> response) {
        if (response.body() instanceof List){
            System.out.println("List<?> size: " + ((List<?>) response.body()).size());
        }
        return response.isSuccessful() && response.body() != null ?
                Either.right(response.body()) :
                Either.left(response.message());
    }
}
