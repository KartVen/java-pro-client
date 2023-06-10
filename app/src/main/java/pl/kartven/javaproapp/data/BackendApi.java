package pl.kartven.javaproapp.data;

import java.util.List;

import pl.kartven.javaproapp.data.model.api.TopicApi;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendApi {
    @GET("api/topics")
    Call<List<TopicApi>> getTopics();
}
