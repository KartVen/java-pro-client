package pl.kartven.javaproapp.data;

import java.util.List;
import java.util.concurrent.Executor;

import pl.kartven.javaproapp.data.model.api.CodeApi;
import pl.kartven.javaproapp.data.model.api.QuizApi;
import pl.kartven.javaproapp.data.model.api.SectionApi;
import pl.kartven.javaproapp.data.model.api.TopicApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendApi {
    @GET("api/topics")
    Call<List<TopicApi>> getTopics();

    @GET("api/topics/{id}/sections")
    Call<List<SectionApi>> getSectionsOfTopic(
            @Path("id") Long id
    );

    @GET("api/topics/{id}/quizzes")
    Call<List<QuizApi>> getQuizzesOfTopic(
            @Path("id") Long id
    );

    @GET("api/sections/{id}/codes")
    Call<List<CodeApi>> getCodesOfSection(
            @Path("id") Long id
    );
}
