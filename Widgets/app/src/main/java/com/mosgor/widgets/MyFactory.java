package com.mosgor.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyFactory implements RemoteViewsService.RemoteViewsFactory {

	Context context;

	int widgetId;

	SimpleDateFormat sdf;

	ArrayList<String> data;

	public MyFactory(Context context, Intent intent) {
		this.context = context;
		widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		sdf = new SimpleDateFormat("HH:mm:ss");
	}

	@Override
	public void onCreate() {
		data = new ArrayList<String>();
	}

	@Override
	public void onDataSetChanged() {
		data.clear();
		data.add(sdf.format(new Date(System.currentTimeMillis())));
		data.add(String.valueOf(hashCode()));
		data.add(String.valueOf(widgetId));
		for (int i = 3; i < 15; i++) {
			data.add("Item " + i);
		}
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item);
		views.setTextViewText(R.id.TVItem, sdf.format(data.get(position)));
		return views;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
}
