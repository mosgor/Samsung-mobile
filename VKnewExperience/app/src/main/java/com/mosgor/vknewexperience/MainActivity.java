package com.mosgor.vknewexperience;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.exceptions.VKApiException;
import com.vk.api.sdk.requests.VKRequest;
import com.vk.api.sdk.utils.VKUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

	TextView textView;

	String myId;

	String[] friendsIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = findViewById(R.id.tv);
		textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

			}
		});
		String[] fingerPrint = VKUtils.getCertificateFingerprint(this, this.getPackageName());
		if (!VK.isLoggedIn())
			authLauncher.launch(new ArrayList<>(Arrays.asList(VKScope.WALL, VKScope.FRIENDS, VKScope.GROUPS)));
		Observable obs = observableIdOwner("mos_gor")
				.map(new Function<String, Integer>() {
					@Override
					public Integer apply(String s) throws Exception {
						return Integer.parseInt(s);
					}
				}).share();
		Observer friendsObserver = new Observer<String[]>() {

			@Override
			public void onSubscribe(@NonNull Disposable d) {

			}

			@Override
			public void onNext(String @NonNull [] strings) {
				friendsIds = strings;
				Log.d("fr1", friendsIds[0]);
			}

			@Override
			public void onError(@NonNull Throwable e) {

			}

			@Override
			public void onComplete() {

			}
		};

		obs.subscribe(new Observer<Integer>() {
			@Override
			public void onSubscribe(@NonNull Disposable d) {

			}

			@Override
			public void onNext(@NonNull Integer integer) {
				textView.setText("" + integer);
				myId = integer.toString();
				Observable friends = observableIdFriends(myId);
				friends.subscribe(friendsObserver);
			}

			@Override
			public void onError(@NonNull Throwable e) {

			}

			@Override
			public void onComplete() {

			}
		});
//		obs.subscribe(new Observer<Integer>() {
//			@Override
//			public void onSubscribe(@NonNull Disposable d) {
//
//			}
//
//			@Override
//			public void onNext(@NonNull Integer integer) {
//				textView.setText("" + integer);
//			}
//
//			@Override
//			public void onError(@NonNull Throwable e) {
//
//			}
//
//			@Override
//			public void onComplete() {
//
//			}
//		});
//		User user = new User();
//		Single single = user.getName()
//				.map(new Function<String, String>() {
//
//					@Override
//					public String apply(String s) throws Throwable {
//						return s.concat("lol");
//					}
//				}).subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread());
//		single.subscribe(new SingleObserver<String>() {
//			@Override
//			public void onSubscribe(@NonNull Disposable d) {
//
//			}
//
//			@Override
//			public void onSuccess(@NonNull String s) {
//
//			}
//
//			@Override
//			public void onError(@NonNull Throwable e) {
//
//			}
//		});
//		Completable complete = user.setName("mos_gor")
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread());
//		complete.subscribe(new CompletableObserver() {
//
//			@Override
//			public void onSubscribe(@NonNull Disposable d) {
//
//			}
//
//			@Override
//			public void onComplete() {
//
//			}
//
//			@Override
//			public void onError(@NonNull Throwable e) {
//
//			}
//		});
	}

	ActivityResultLauncher<Collection<VKScope>> authLauncher = VK.login(this, o -> {

	});

	public String getIdOwner(String screenname) {
		String id = "0";
		VKRequest<JSONObject> r = new VKRequest("users.get", VK.getApiVersion()).addParam("user_ids", screenname);
		try {
			id = VK.executeSync(r)
					.getJSONArray("response")
					.getJSONObject(0)
					.getString("id"); //sync в главном потоке, просто execute в другом
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (VKApiException e) {
			throw new RuntimeException(e);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return id;
	}

	public Observable observableIdOwner(String screenname) {
		return Observable.fromCallable(new Callable<String>() {
					@Override
					public String call() throws Exception {
						return getIdOwner(screenname);
					}
				}).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
					@Override
					public ObservableSource<? extends String> apply(Throwable throwable) throws Throwable {
						return Observable.just("0");
					}
				})
				.subscribeOn(Schedulers.io()) // выполнение операций в фоновом режиме
				.observeOn(AndroidSchedulers.mainThread()); // наблюдение в главном потоке
	}

	public String[] getIdFriends(String id) throws JSONException {
		String[] ids = new String[10];
		VKRequest<JSONObject> r = new VKRequest("friends.get", VK.getApiVersion())
				.addParam("user_id", id)
				.addParam("count", 10);
		JSONArray items;
		try {
			items = VK.executeSync(r)
					.getJSONObject("response")
					.getJSONArray("items");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (VKApiException e) {
			throw new RuntimeException(e);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < 10; i++) {
			ids[i] = items.getString(i);
		}
		return ids;
	}

	public Observable observableIdFriends(String id) {
		return Observable.fromCallable(new Callable<String[]>() {
					@Override
					public String[] call() throws Exception {
						return getIdFriends(id);
					}
				}).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String[]>>() {
					@Override
					public ObservableSource<? extends String[]> apply(Throwable throwable) throws Throwable {
						return null;
					}
				})
				.subscribeOn(Schedulers.io()) // выполнение операций в фоновом режиме
				.observeOn(AndroidSchedulers.mainThread()); // наблюдение в главном потоке
	}
}