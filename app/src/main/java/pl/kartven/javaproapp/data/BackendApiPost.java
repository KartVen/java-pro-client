package pl.kartven.javaproapp.data;

import java.util.List;

import okhttp3.MultipartBody;
import pl.kartven.javaproapp.data.model.api.AuthApi;
import pl.kartven.javaproapp.data.model.api.TopicApi;
import pl.kartven.javaproapp.data.model.api.request.CodeReqApi;
import pl.kartven.javaproapp.data.model.api.request.LinkReqApi;
import pl.kartven.javaproapp.data.model.api.request.LoginApi;
import pl.kartven.javaproapp.data.model.api.request.QuizReqApi;
import pl.kartven.javaproapp.data.model.api.request.RegisterApi;
import pl.kartven.javaproapp.data.model.api.request.TopicReqApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BackendApiPost {
    @POST("api/login")
    Call<AuthApi> postLogin(
            @Body LoginApi loginApi
    );

    @POST("api/register")
    Call<AuthApi> postRegister(
            @Body RegisterApi registerDto
    );

    @POST("/api/topics")
    Call<TopicApi> postTopic(
            @Header("Authorization") String bearer,
            @Body TopicReqApi topicReqApi
    );

    @Multipart
    @POST("/{id}/slides")
    Call<Void> postSlidesOfTopic(
            @Header("Authorization") String bearer,
            @Path("id") Long id,
            @Part("slides") List<MultipartBody.Part> slides
    );

    @POST("/api/topics/{id}/codes")
    Call<Void> postCodeOfTopic(
            @Header("Authorization") String bearer,
            @Path("id") Long id,
            @Body CodeReqApi codeReqApi
    );

    @POST("/api/topics/{id}/links")
    Call<Void> postLinkOfTopic(
            @Header("Authorization") String bearer,
            @Path("id") Long id,
            @Body LinkReqApi linkReqApi
    );

    @POST("/api/topics/{id}/quizzes")
    Call<Void> postQuizOfTopic(
            @Header("Authorization") String bearer,
            @Path("id") Long id,
            @Body QuizReqApi quizReqApi
    );
}
