package pl.kartven.javaproapp.data;

import pl.kartven.javaproapp.data.model.api.AuthDto;
import pl.kartven.javaproapp.data.model.api.request.LoginDto;
import pl.kartven.javaproapp.data.model.api.request.RegisterDto;
import pl.kartven.javaproapp.data.model.api.request.TopicReqApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BackendApiPost {
    @POST("api/login")
    Call<AuthDto> postLogin(
            @Body LoginDto loginDto
    );

    @POST("api/register")
    Call<AuthDto> postRegister(
            @Body RegisterDto registerDto
    );

    @POST("/api/topics")
    Call<Void> postTopic(
            @Header("Authorization") String bearer,
            @Body TopicReqApi topicReqApi
    );
}
