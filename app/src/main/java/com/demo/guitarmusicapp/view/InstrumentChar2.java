package com.demo.guitarmusicapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.demo.guitarmusicapp.util.ScreenUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InstrumentChar2 extends View {
    private Context context;
    private double value = 0;
    private double value2 = 0;
    private List<Scale> scales = new ArrayList<>();

    public InstrumentChar2(Context context) {
        this(context, null);
    }

    public InstrumentChar2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (this.context == null) {
            this.context = context;
        }
        init();
    }

    private void init() {
        scales.add(new Scale("C1", 63.375));
        //  scales.add(new Scale("E1", 82.372));
        scales.add(new Scale("G1", 97.931));
        //scales.add(new Scale("A1", 110.000));
        scales.add(new Scale("C", 130.750));
        scales.add(new Scale("D", 146.832));
        scales.add(new Scale("F", 174.551));
        scales.add(new Scale("G", 195.997));
        scales.add(new Scale("A", 219.921));
        scales.add(new Scale("B", 246.941));
        scales.add(new Scale("d1", 293.400));
        scales.add(new Scale("e1", 329.627));
    }

    public void updateView(double value) {
        this.value2 = value;
        invalidate();
    }

    public void insertValues(double value) {
        this.value = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#2b2b2b"));
        paint.setStrokeWidth(10);
        int intervalW = getWidth() / 20;
        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(intervalW * i, getHeight(), intervalW * i, (getHeight() / 3), paint);
            } else {
                canvas.drawLine(intervalW * i, getHeight(), intervalW * i, (getHeight() / 3 * 2), paint);
            }
        }
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(3);
        float displacement2 = (float) (getWidth() * div(value2, 400.00, 1));
        canvas.drawLine(displacement2, getHeight(), displacement2, getHeight() / 2, paint);
        //写字
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(3);
        paint.setTextSize(ScreenUtil.dip2px(context, 25));
        canvas.drawText(value2 + "HZ", getWidth() / 2 - getTextWidth(paint, value2 + "HZ") / 2, getHeight() / 2, paint);
        //画各个音阶
        //C1:63.375 E1:82.3725 G1:97.93175 A1:110.0000 C:130.75 D:146.8324 F:174.5513 G = 195.9977
        //A:219.9215 B = 246.9417 d1:293.4 e1:329.6276
        for (int i = 0; i < scales.size(); i++) {
            paint.setColor(Color.parseColor("#4678ff"));
            paint.setStrokeWidth(3);
            paint.setTextSize(ScreenUtil.dip2px(context, 18));
            float x = (float) (getWidth() * div(scales.get(i).getB(), 400.00, 4));
            canvas.drawText(scales.get(i).getA(), x, getHeight() / 4 * 3, paint);
        }
        //标准值
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(3);
        paint.setTextSize(ScreenUtil.dip2px(context, 20));
        canvas.drawText(value + "HZ", getWidth() - getTextWidth(paint, value + "HZ") - 50, getHeight() / 6, paint);
    }

    /**
     * 获取文本的宽度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    class Scale {
        private String a;
        private Double b;

        public Scale(String a, Double b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public Double getB() {
            return b;
        }

        public void setB(Double b) {
            this.b = b;
        }
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
