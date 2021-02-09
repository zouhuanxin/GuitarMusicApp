package com.demo.guitarmusicapp;

import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.guitarmusicapp.util.NavigationManager;
import com.demo.guitarmusicapp.util.PermissionUtils;
import com.demo.guitarmusicapp.view.InstrumentChar;
import com.zhx.myrounded.RoundedTextView;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int frequency = 11025;
    int buffsize = 8 * 1024;
    AudioRecord audioRecord;
    private static final int[] OPT_SAMPLE_RATES = {11025, 8000, 22050, 44100};
    private static final int[] BUFFERSIZE_PER_SAMPLE_RATE = {8 * 1024,
            4 * 1024, 16 * 1024, 32 * 1024};
    private RecordPlayThread recordPlayThread;
    private double Eright = 329.6276, B = 246.9417, G = 195.9977, D = 146.8324, A = 110.0000, Eleft = 82.4069;

    private InstrumentChar resultChar;
    private TextView result;
    private RoundedTextView t1;
    private RoundedTextView t2;
    private RoundedTextView t3;
    private RoundedTextView t4;
    private RoundedTextView t5;
    private RoundedTextView t6;
    private ImageView reset;
    private TextView frequent;

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
        NavigationManager.setBottomNavigationColor(this);
        new PermissionUtils().verifyStoragePermissions(this, null);
        initRecord();
        initAudioRecord();
        initView();
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
                        String[] arr = String.valueOf(frequency).split("\\.");
                        if (arr.length != 2) {
                            return;
                        }
                        if (arr[1].length() < 4) {
                            return;
                        }
                        double value = Double.parseDouble(arr[0] +"."+ arr[1].substring(0, 4));
                        result.setText(value + "hz");
                        resultChar.updateView(value);
                        if (value == Eright) {
                            t6.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (value == B) {
                            t4.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (value == G) {
                            t2.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (value == D) {
                            t1.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (value == A) {
                            t3.setBackgroundColor(Color.parseColor("#60ffffff"));
                        } else if (value == Eleft) {
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
//        t1.setBackgroundColor(Color.parseColor("#00ffffff"));
//        t2.setBackgroundColor(Color.parseColor("#00ffffff"));
//        t3.setBackgroundColor(Color.parseColor("#00ffffff"));
//        t4.setBackgroundColor(Color.parseColor("#00ffffff"));
//        t5.setBackgroundColor(Color.parseColor("#00ffffff"));
//        t6.setBackgroundColor(Color.parseColor("#00ffffff"));
        t1.setBackgroundResource(R.drawable.button_style1);
        t2.setBackgroundResource(R.drawable.button_style1);
        t3.setBackgroundResource(R.drawable.button_style1);
        t4.setBackgroundResource(R.drawable.button_style1);
        t5.setBackgroundResource(R.drawable.button_style1);
        t6.setBackgroundResource(R.drawable.button_style1);
    }

    private void initView() {
        resultChar = (InstrumentChar) findViewById(R.id.result_char);
        result = (TextView) findViewById(R.id.result);
        t1 = (RoundedTextView) findViewById(R.id.t1);
        t2 = (RoundedTextView) findViewById(R.id.t2);
        t3 = (RoundedTextView) findViewById(R.id.t3);
        t4 = (RoundedTextView) findViewById(R.id.t4);
        t5 = (RoundedTextView) findViewById(R.id.t5);
        t6 = (RoundedTextView) findViewById(R.id.t6);
        reset = (ImageView) findViewById(R.id.reset);
        frequent = (TextView) findViewById(R.id.frequent);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViewBack();
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
        switch (v.getId()) {
            case R.id.t1:
                Toast.makeText(MainActivity.this, D+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(D+"HZ");
                resultChar.insertValues(D);
                break;
            case R.id.t2:
                Toast.makeText(MainActivity.this, G+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(G+"HZ");
                resultChar.insertValues(G);
                break;
            case R.id.t3:
                Toast.makeText(MainActivity.this, A+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(A+"HZ");
                resultChar.insertValues(A);
                break;
            case R.id.t4:
                Toast.makeText(MainActivity.this, B+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(B+"HZ");
                resultChar.insertValues(B);
                break;
            case R.id.t5:
                Toast.makeText(MainActivity.this, Eleft+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(Eleft+"HZ");
                resultChar.insertValues(Eleft);
                break;
            case R.id.t6:
                Toast.makeText(MainActivity.this, Eright+"HZ", Toast.LENGTH_SHORT).show();
                frequent.setText(Eright+"HZ");
                resultChar.insertValues(Eright);
                break;
        }
    }
}