package pl.kartven.javaproapp.data;

import java.util.List;
import java.util.concurrent.Executor;

import pl.kartven.javaproapp.data.model.api.CodeApi;
import pl.kartven.javaproapp.data.model.api.LinkApi;
import pl.kartven.javaproapp.data.model.api.QuestionApi;
import pl.kartven.javaproapp.data.model.api.QuizApi;
import pl.kartven.javaproapp.data.model.api.QuizDetailsApi;
import pl.kartven.javaproapp.data.model.api.SectionApi;
import pl.kartven.javaproapp.data.model.api.TopicApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendApi {
    Api API = Api.DORMITORY;

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

    enum Api {
        DORMITORY("http://192.168.10.106:8444"),
        COMPANY("http://19.16.13.171:8444"),
        PHONE("http://192.168.43.239:8444"),
        ALWAYSDATA("http://krystianus.alwaysdata.net");

        private final String url;

        Api(String url){
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
