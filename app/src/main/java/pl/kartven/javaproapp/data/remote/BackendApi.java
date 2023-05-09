package pl.kartven.javaproapp.data.remote;

import com.google.common.net.HttpHeaders;

import java.util.List;

import pl.kartven.javaproapp.data.model.AuthApi;
import pl.kartven.javaproapp.data.model.AuthLoginRequest;
import pl.kartven.javaproapp.data.model.AuthRegisterRequest;
import pl.kartven.javaproapp.data.model.SingleRangeApiDetails;
import pl.kartven.javaproapp.data.model.SlideApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BackendApi {
    @POST("/api/login")
    Call<AuthApi> login(@Body AuthLoginRequest body);

    @POST("/api/register")
    Call<AuthApi> register(@Body AuthRegisterRequest body);

    @GET("/api/lectures")
    Call<List<SingleRangeApiDetails>> getRangeList(
            @Header("Authorization") String authHeader
    );

    @GET("api/lectures/{id}")
    Call<List<SlideApi>> getLectureSlides(
            @Path("id") Long id,
            @Header("Authorization") String authHeader
    );
}
