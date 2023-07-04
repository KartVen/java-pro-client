package pl.kartven.javaproapp.data;

import java.util.List;

import pl.kartven.javaproapp.data.model.api.CodeApi;
import pl.kartven.javaproapp.data.model.api.LinkApi;
import pl.kartven.javaproapp.data.model.api.QuestionApi;
import pl.kartven.javaproapp.data.model.api.QuizApi;
import pl.kartven.javaproapp.data.model.api.QuizDetailsApi;
import pl.kartven.javaproapp.data.model.api.SectionApi;
import pl.kartven.javaproapp.data.model.api.SlideApi;
import pl.kartven.javaproapp.data.model.api.TopicApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendApi extends BackendApiPost {
    @GET("api/topics")
    Call<List<TopicApi>> getTopics();

    @GET("api/topics")
    Call<List<TopicApi>> getMyTopics(
            @Query("user_id") Long userId
    );

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

    @GET("api/quizzes/{id}/questions")
    Call<List<QuestionApi>> getQuestionsOfQuiz(
            @Path("id") Long id
    );

    @GET("api/quizzes/{id}")
    Call<QuizDetailsApi> getQuizDetails(
            @Path("id") Long id
    );

    @GET("api/sections/{id}/links")
    Call<List<LinkApi>> getLinksOfSection(
            @Path("id") Long id
    );

    @GET("api/topics/{id}/slides")
    Call<List<SlideApi>> getSlidesOfTopic(
            @Path("id") Long id,
            @Query("page") Integer page,
            @Query("size") Integer size
    );
}
