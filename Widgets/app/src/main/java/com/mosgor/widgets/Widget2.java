package com.mosgor.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Widget2 extends AppWidgetProvider {

	ConstraintLayout l;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int widgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, widgetId);
		}
	}

	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget2);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
					views.setColorInt(R.id.wid, "setBackground", Color.RED, Color.BLUE);
				}
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
	public void onEnabled(Context context) {
		super.onEnabled(context);

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}
}
