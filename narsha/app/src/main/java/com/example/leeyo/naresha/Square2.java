package com.example.leeyo.naresha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Square2 extends View {
    private Paint paint;

    public Square2(Context context) {
        super(context);

    }

    public Square2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint background = new Paint();
        Paint Date = new Paint();
        Paint date1 = new Paint();
        Paint date2 = new Paint();
        Paint date3 = new Paint();
        Paint date4 = new Paint();
        Paint date5 = new Paint();
        Paint date6 = new Paint();

        //int color = ResourcesCompat.getColor(getResources(), R.color.Gray, null);
        //Paint paint = new Paint();

        background.setColor(Color.LTGRAY);
        Date.setColor(Color.BLACK);
        Date.setTextSize(75);
        date1.setTextSize(35);
        date2.setTextSize(35);
        date3.setTextSize(35);
        date4.setTextSize(35);
        date5.setTextSize(35);
        date6.setTextSize(35);

        canvas.drawRect(10, 10, canvas.getWidth() - 20, canvas.getHeight(), background);
        canvas.drawText("2018-09-29", 35, 90, Date);
        canvas.drawText("AM  07:53   -   120/80", 50, 170, date1);
        canvas.drawText("AM  09:12   -   129/87", 50, 220, date2);
        canvas.drawText("AM  11:29   -   135/93", 50, 270, date3);
        canvas.drawText("PM  02:22   -   128/89", 50, 320, date4);
        canvas.drawText("PM  05:04   -   123/84", 50, 370, date5);
        canvas.drawText("PM  08:42   -   121/81", 50, 420, date6);

    }

}
