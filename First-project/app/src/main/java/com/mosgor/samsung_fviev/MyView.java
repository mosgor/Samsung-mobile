package com.mosgor.samsung_fviev;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Random;

public class MyView extends View {

    int[] xScore = new int[5];
    int[] yScore = new int[5];

    Random random = new Random();

    public MyView(Context context) {
        super(context);
        //random.setSeed(1000);
        for (int i = 0; i < 5; i++) {
            xScore[i] = random.nextInt() % 15 - 5;
            yScore[i] = random.nextInt() % 15 - 5;
        }
    }

    int[] xCoord = new int[5];
    int[] yCoord = new int[5];

    boolean enter = true;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (enter){
            for (int i = 0; i < 5; i++) {
                xCoord[i] = canvas.getWidth() / 2;
                yCoord[i] = canvas.getHeight() / 2;
            }
            enter = false;
        }
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 100, paint);

        /*for(int i = 0; i < canvas.getHeight(); i += 10){
            canvas.drawLine(0, i, canvas.getWidth(), i, paint);
        }*/

        for (int i = 0; i < 5; i++) {
            xCoord[i] += xScore[i];
            yCoord[i] += yScore[i];
            canvas.drawCircle(xCoord[i], yCoord[i], 100, paint);
            if (xCoord[i] <= 0 || yCoord[i] <= 0
                    || xCoord[i] >= canvas.getWidth() || yCoord[i] >= canvas.getHeight()){
                xCoord[i] = canvas.getWidth() / 2;
                yCoord[i] = canvas.getHeight() / 2;
                xScore[i] = random.nextInt() % 15 - 5;
                yScore[i] = random.nextInt() % 15 - 5;
            }
        }

        invalidate(); // обновляет кадры

    }
}
