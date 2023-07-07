package pl.kartven.javaproapp.utils.utility;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Date;

import io.vavr.control.Either;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ResponseUtils {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    private ResponseUtils() {
    }

    public static <S> void onFailure(MutableLiveData<Resource<S>> liveData, Throwable e) {
        liveData.setValue(onFailure(e));
    }

    public static <S> Resource.Error<S> onFailure(Throwable e) {
        Log.e("ApiRequest", "onFailure", e);
        return new Resource.Error<S>(Constant.Expression.SERVER_CONNECTION_ERROR, null);
    }

    public static <T> Either<String, T> onResponse(Response<T> response) {
        if (response.isSuccessful()) {
            Log.i("ApiRequest", "onResponse: SUCCESS");
            return Either.right(response.body());
        } else {
            String message = parseError(response).getMessage();
            Log.i("ApiRequest", "onResponse: ERROR: " + message);
            return Either.left(message);
        }
    }

    private static ErrorApi parseError(final Response<?> response) {
        JSONObject bodyObj;
        ErrorApi errorApi = null;
        try (ResponseBody errorBody = response.errorBody()) {
            String errorBodyString = errorBody.string();
            if (errorBodyString != null) {
                bodyObj = new JSONObject(errorBodyString);
                errorApi = new ErrorApi(
                        bodyObj.getString("timeStamp"),
                        bodyObj.getInt("status"),
                        bodyObj.getString("error"),
                        bodyObj.getString("message")
                );
                return errorApi;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ErrorApi(new Date().toString(), -1, "Error", response.message());
    }

    public static class ErrorApi {
        private String timeStamp;
        private int status;
        private String error;
        private String message;

        public ErrorApi(String timeStamp, int status, String error, String message) {
            this.timeStamp = timeStamp;
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
