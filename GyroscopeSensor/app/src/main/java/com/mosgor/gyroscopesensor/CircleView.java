package com.mosgor.gyroscopesensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

public class CircleView extends View {

	Circle circle;

	public CircleView(Context context, Circle circle){
		super(context);
		this.circle = circle;
	}

	boolean first = true;

	@Override
	protected void onDraw(@NonNull Canvas canvas) {
		super.onDraw(canvas);
		if (first){
			circle.setMax(getWidth(), getHeight());
			first = false;
		}
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(circle.x, circle.y, 100, paint);
		circle.move();
		invalidate();
	}
}
