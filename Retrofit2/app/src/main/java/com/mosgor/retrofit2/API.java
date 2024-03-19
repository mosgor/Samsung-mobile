package com.mosgor.retrofit2;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
	@GET("/api/tags")
	Call<List<Tag>> getTags(); // call для обработки пустого ответа от сервера

	@POST("/api/tags/{tagId}/likes")
	Call<Tag> likeTag(@Path("tagId") String id);

	@POST("/api/auth/jwt/login")
	Call<BearerResponse> login(@Body RequestBody requestBody);

	@POST("/api/tags/")
	Call<Tag> postTag(@Body RequestBody requestBody);

}
