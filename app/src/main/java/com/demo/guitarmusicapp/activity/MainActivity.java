package com.demo.guitarmusicapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.demo.guitarmusicapp.R;
import com.demo.guitarmusicapp.util.NavigationManager;
import com.demo.guitarmusicapp.util.PermissionUtils;
import com.demo.guitarmusicapp.view.InstrumentChar;
import com.demo.guitarmusicapp.view.InstrumentChar2;
import com.zhx.myrounded.RoundedTextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int frequency = 11025;
    int buffsize = 8 * 1024;
    AudioRecord audioRecord;
    private static final int[] OPT_SAMPLE_RATES = {11025, 8000, 22050, 44100};
    private static final int[] BUFFERSIZE_PER_SAMPLE_RATE = {8 * 1024,
            4 * 1024, 16 * 1024, 32 * 1024};
    private RecordPlayThread recordPlayThread;
    private double Eright = 329.6276, B = 246.9417, G = 195.9977, D = 146.8324, A = 110.0000, Eleft = 82.4069;
    //精确度
    //G:195 B:246 Eright:329 D:146 A:110 Elfet:82
    private int precision = 1;

    private FrameLayout back1;
    private RoundedTextView result;
    private RoundedTextView frequent;
    private RoundedTextView t1;
    private RoundedTextView t2;
    private RoundedTextView t3;
    private RoundedTextView t4;
    private RoundedTextView t5;
    private RoundedTextView t6;
    private ImageView reset;
    private TextView goAccurateModel;
    private FrameLayout mainGroup2;
    private InstrumentChar resultChar;
    private TextView result2;
    private TextView closeAccurateModel;

    private MainViewModel mainViewModel;
    public static MainActivity mainActivity = null;
    private LinearLayout mainGroup;

    private InstrumentChar2 char2;

    // 每个device的初始化参数可能不同
    private void initAudioRecord() {
        int counter = 0;
        for (int sampleRate : OPT_SAMPLE_RATES) {
            initRecord();
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                frequency = sampleRate;
                buffsize = BUFFERSIZE_PER_SAMPLE_RATE[counter];
                break;
            }
            counter++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        mainViewModel = new ViewModelProvider(mainActivity).get(MainViewModel.class);
        NavigationManager.setBottomNavigationColor(this);
        initView();
        new PermissionUtils().verifyStoragePermissions(this, null);
        initRecord();
        initAudioRecord();
        startMusic();
    }

    private void initRecord() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                frequency, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, frequency * 6);
    }

    private void startMusic() {
        recordPlayThread = new RecordPlayThread(audioRecord, true, frequency, buffsize);
        recordPlayThread.setDataCallBack(new RecordPlayThread.DataCallBack() {
            @Override
            public void data(double frequency) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result2.setText(new BigDecimal(frequency).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "HZ");
                        resultChar.updateView(new BigDecimal(frequency).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
                        double value = new BigDecimal(frequency).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
                        result.setText(value + "HZ");
                        char2.updateView(value);
                        if (checkFrequency(value, new BigDecimal(Eright).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t6.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (checkFrequency(value, new BigDecimal(B).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t4.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (checkFrequency(value, new BigDecimal(G).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t2.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (checkFrequency(value, new BigDecimal(D).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t1.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (checkFrequency(value, new BigDecimal(A).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t3.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (checkFrequency(value, new BigDecimal(Eleft).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue())) {
                            t5.setBackgroundColor(Color.parseColor("#60ffffff"));
                        }
                    }
                });
            }
        });
        recordPlayThread.start();
    }

    /**
     * 取消所有按钮状态
     */
    private void clearViewBack() {
        t1.setBackgroundResource(R.drawable.button_style1);
        t2.setBackgroundResource(R.drawable.button_style1);
        t3.setBackgroundResource(R.drawable.button_style1);
        t4.setBackgroundResource(R.drawable.button_style1);
        t5.setBackgroundResource(R.drawable.button_style1);
        t6.setBackgroundResource(R.drawable.button_style1);
    }

    private void initView() {
        mainGroup = (LinearLayout) findViewById(R.id.main_group);
        back1 = (FrameLayout) findViewById(R.id.back1);
        t1 = (RoundedTextView) findViewById(R.id.t1);
        t2 = (RoundedTextView) findViewById(R.id.t2);
        t3 = (RoundedTextView) findViewById(R.id.t3);
        t4 = (RoundedTextView) findViewById(R.id.t4);
        t5 = (RoundedTextView) findViewById(R.id.t5);
        t6 = (RoundedTextView) findViewById(R.id.t6);
        reset = (ImageView) findViewById(R.id.reset);
        result = (RoundedTextView) findViewById(R.id.result);
        frequent = (RoundedTextView) findViewById(R.id.frequent);
        goAccurateModel = (TextView) findViewById(R.id.go_accurate_model);
        //精确模式
        mainGroup2 = (FrameLayout) findViewById(R.id.main_group2);
        resultChar = (InstrumentChar) findViewById(R.id.result_char);
        result2 = (TextView) findViewById(R.id.result2);
        closeAccurateModel = (TextView) findViewById(R.id.close_accurate_model);
        char2 = (InstrumentChar2) findViewById(R.id.char2);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                //clearViewBack();
            }
        });

        goAccurateModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入精准模式
                mainGroup2.setVisibility(View.VISIBLE);
            }
        });
        closeAccurateModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入精准模式
                mainGroup2.setVisibility(View.GONE);
            }
        });

        mainViewModel.getBackColor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mainGroup.setBackgroundColor(Color.parseColor(s));
                mainGroup2.setBackgroundColor(Color.parseColor(s));
            }
        });
        mainViewModel.getFrequencybackcolor().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                back1.setBackgroundColor(Color.parseColor(s));
                result.setBackgroundColor(Color.parseColor(s));
                frequent.setBackgroundColor(Color.parseColor(s));
                resultChar.setBackgroundColor(Color.parseColor(s));
            }
        });

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String value;
        switch (v.getId()) {
            case R.id.t1:
                value = String.valueOf(new BigDecimal(D).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, value + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
            case R.id.t2:
                value = String.valueOf(new BigDecimal(G).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, value + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
            case R.id.t3:
                value = String.valueOf(new BigDecimal(A).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, A + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
            case R.id.t4:
                value = String.valueOf(new BigDecimal(B).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, value + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
            case R.id.t5:
                value = String.valueOf(new BigDecimal(Eleft).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, value + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
            case R.id.t6:
                value = String.valueOf(new BigDecimal(Eright).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue());
                Toast.makeText(MainActivity.this, value + "HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(value + "HZ");
                char2.insertValues(Double.parseDouble(value));
                break;
        }
    }

    /**
     * 判断频率是否准备 上下不超过3
     */
    private boolean checkFrequency(double v1, double v2) {
        double va = Math.abs(v1 - v2);
        if (va < 0.3) {
            return true;
        }
        return false;
    }
}