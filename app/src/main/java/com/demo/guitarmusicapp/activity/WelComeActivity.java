package com.demo.guitarmusicapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.guitarmusicapp.R;
import com.demo.guitarmusicapp.util.NavigationManager;
import com.hanks.htextview.base.HTextView;


public class WelComeActivity extends AppCompatActivity {
    private HTextView hTextView;
    private ImageView next;

    private String[] arrs = {"无广告","免费，好用。","极简，高效，精确。"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#f5f6f9"));
        }
        NavigationManager.setStatusBarColor(Color.parseColor("#f5f6f9"),this);
        setContentView(R.layout.activity_wel_come);
        hTextView = (HTextView) findViewById(R.id.htext);
        next = (ImageView) findViewById(R.id.next);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<arrs.length;i++){
                    hTextView.animateText(arrs[i]);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(next, "translationX", 0f, dip);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(next, "rotation", 0f, 720f);
                animatorSet.playTogether(objectAnimator1,objectAnimator2);
                animatorSet.setDuration(300);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

}