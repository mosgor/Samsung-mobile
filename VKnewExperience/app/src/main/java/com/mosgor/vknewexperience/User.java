package com.mosgor.vknewexperience;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class User {
	String name = "";

	public Single getName () {
		return Single.just(name);
	}

	public Completable setName (String name) {
		if (this.name.equals(name)) {
			return Completable.error(new Throwable());
		}
		this.name = name;
		return Completable.complete();
	}
}
