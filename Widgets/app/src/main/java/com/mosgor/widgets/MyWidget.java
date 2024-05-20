package com.mosgor.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d("UPDATE", "onUpdate");
		for (int widgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, widgetId);
		}
	}

	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
				views.setTextViewText(R.id.a, "Widget 2");
				appWidgetManager.updateAppWidget(widgetId, views);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.d("DELETE", "onDelete");
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.d("ENABLED", "onEnabled");
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.d("DISABLED", "onDisabled");
	}
}
