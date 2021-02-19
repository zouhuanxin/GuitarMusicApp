package com.demo.guitarmusicapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

public class HorizontalInstrumentChar extends View {
    private Context context;
    private double value = 250;
    private double value2 = 0;

    public HorizontalInstrumentChar(Context context) {
        this(context, null);
    }

    public HorizontalInstrumentChar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (this.context == null) {
            this.context = context;
        }
        init();
    }

    private void init() {

    }

    public void updateView(double value) {
        this.value = value;
        invalidate();
    }

    public void insertValues(double value) {
        this.value2 = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
//        paint.setColor(Color.parseColor("#202020"));
//        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
//        canvas.drawRect(rectF, paint);
        paint.setColor(Color.parseColor("#2b2b2b"));
        paint.setStrokeWidth(10);
        int intervalW = getHeight() / 20;
        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0, intervalW * i, (getWidth() / 3 * 2), intervalW * i, paint);
            } else {
                canvas.drawLine(0, intervalW * i, (getWidth() / 3), intervalW * i, paint);
            }
        }
        paint.setColor(Color.parseColor("#737373"));
        paint.setStrokeWidth(5);
        float displacement = (float) (getHeight() * div(value, 500.00, 1));
        canvas.drawLine(displacement, getHeight(), displacement, 0, paint);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(3);
        float displacement2 = (float) (getHeight() * div(value2, 500.00, 1));
        canvas.drawLine(0, displacement2, getWidth() / 2, displacement2, paint);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
