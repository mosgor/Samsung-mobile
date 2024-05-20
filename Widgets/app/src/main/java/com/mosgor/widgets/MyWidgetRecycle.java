package com.mosgor.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;

public class MyWidgetRecycle extends AppWidgetProvider {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int widgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, widgetId);
		}
	}

	public void SetListClick(RemoteViews rv) {}

	public void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {
		Intent updIntent = new Intent(context, MyWidgetRecycle.class);
		updIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		updIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { appWidgetId });
		PendingIntent updPIntent = PendingIntent.getBroadcast(context, appWidgetId, updIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setOnClickPendingIntent(R.id.a, updPIntent);
	}

	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		Intent adapter = new Intent(context, MyService.class);
		adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		rv.setRemoteAdapter(R.id.lv, adapter);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}
}
