package pl.kartven.javaproapp.utils.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import io.vavr.control.Either;
import pl.kartven.javaproapp.utils.utility.Resource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackAdapter<T> implements Callback<T> {
    protected <S> void onFailure(MutableLiveData<Resource<S>> liveData) {
        liveData.setValue(new Resource.Error<S>("Couldn't reach server", null));
    }

    protected <S> Resource<S> onFailure(Resource<S> resourceData) {
        return new Resource.Error<S>("Couldn't reach server", null);
    }

    protected Either<String, T> onResponse(Response<T> response) {
        return response.isSuccessful() && response.body() != null ?
                Either.right(response.body()) :
                Either.left(response.message());
    }

    @Override
    public abstract void onResponse(@NonNull Call<T> call, @NonNull Response<T> response);

    @Override
    public abstract void onFailure(@NonNull Call<T> call, @NonNull Throwable t);
}
