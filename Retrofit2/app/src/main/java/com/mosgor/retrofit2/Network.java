package com.mosgor.retrofit2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import kotlin.jvm.Synchronized;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
	Retrofit retrofit;

	API api;

	//String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI3Y2VhNjk3My0wN2MyLTQ2MDEtODg1Zi1kMjJjYmI3YmYwZTIiLCJhdWQiOlsiZmFzdGFwaS11c2VyczphdXRoIl19.mq5oebg5IdHoj25N2EEgmq6O1Iuod1WBMTH5X4tBldA";

	String token = "";

	public Network() {
		retrofit = new Retrofit.Builder()
				.baseUrl("https://maps.rtuitlab.dev/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		api = retrofit.create(API.class);
	}

	public void authorizationMoment(){
		if (token.compareTo("")!=0){
			OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
				@Override
				public okhttp3.Response intercept(Chain chain) throws IOException {
					Request newRequest = chain.request().newBuilder()
							.addHeader("Authorization", "Bearer " + token)
							.build();
					return chain.proceed(newRequest);
				}
			}).build();
			retrofit = new Retrofit.Builder()
					.client(client)
					.baseUrl("https://maps.rtuitlab.dev/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			api = retrofit.create(API.class);
		}
	}

	public void getTags(Handler handler) {
		Call<List<Tag>> call = api.getTags();
		call.enqueue(new Callback<List<Tag>>() {  // enqueue делает во внешнем потоке
			@Override
			public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
				Log.d("RESPONSE", response.body().toString());
				Log.d("RESPONSE2", response.raw().toString());

				Message msg = new Message();
				msg.obj = response.body();
				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Call<List<Tag>> call, Throwable t) {
				Log.d("Fail", t.toString());
			}
		});
	}

	public void likeTag(String id, Handler handler) {
		Call<Tag> call = api.likeTag(id);
		call.enqueue(new Callback<Tag>() {
			@Override
			public void onResponse(Call<Tag> call, Response<Tag> response) {
				Log.d("RESPONSE", response.toString());
				Log.d("RESPONSE2", response.raw().toString());

				Message msg = new Message();
				msg.obj = response.body();
				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Call<Tag> call, Throwable t) {
				Log.d("Fail", t.toString());
			}
		});
	}

	public void login(Handler handlerLogin){
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("username", "mos_gor")
				.addFormDataPart("password", "12345").build();
		Call<BearerResponse> call = api.login(requestBody);
		call.enqueue(new Callback<BearerResponse>() {
			@Override
			public void onResponse(Call<BearerResponse> call, Response<BearerResponse> response) {
				synchronized(token){
					token = response.body().access_token;
				}
				authorizationMoment();
				handlerLogin.sendEmptyMessage(1);
			}

			@Override
			public void onFailure(Call<BearerResponse> call, Throwable t) {
				Log.d("FailLogin", t.toString());
			}
		});
	}

	public void postTag(Handler handler){
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("latitude", "55.669931")
				.addFormDataPart("longitude", "37.479869")
				.addFormDataPart("description", "РТУ МИРЭА").build();
		Call<Tag> call = api.postTag(requestBody);
		call.enqueue(new Callback<Tag>() {
			@Override
			public void onResponse(Call<Tag> call, Response<Tag> response) {
				Log.d("ResponsePost", response.toString());
				Log.d("ResponsePost2", response.raw().toString());

				Message msg = new Message();
				msg.obj = response.body();
				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Call<Tag> call, Throwable t) {
				Log.d("Fail", t.toString());
			}
		});
	}
}
