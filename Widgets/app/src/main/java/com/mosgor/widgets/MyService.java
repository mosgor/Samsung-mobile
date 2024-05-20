package com.mosgor.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyService extends RemoteViewsService {
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new MyFactory(getApplicationContext(), intent);
	}
}
