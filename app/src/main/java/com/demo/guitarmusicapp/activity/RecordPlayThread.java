package com.demo.guitarmusicapp.activity;

import android.media.AudioRecord;
import android.media.AudioTrack;

public class RecordPlayThread extends Thread{

    static {
        System.loadLibrary("mpm");
    }

    public interface DataCallBack{
        void data(double frequency);
    }
    private DataCallBack dataCallBack;
    private AudioRecord audioRecord;
    private boolean isRecording;
    private int frequency;
    private int buffsize;

    public RecordPlayThread(AudioRecord audioRecord, boolean isRecording, int frequency,int buffsize){
        this.audioRecord = audioRecord;
        this.isRecording = isRecording;
        this.frequency = frequency;
        this.buffsize = buffsize;
    }

    public void setDataCallBack(DataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }

    public native double get_pitch_from_short(short[] data, int sampleRate);

    @Override
    public void run() {
        try {
            short[] buffer = new short[this.buffsize];
            audioRecord.startRecording();//开始录制
            while (isRecording) {
                //从MIC保存数据到缓冲区
                int bufferReadResult = audioRecord.read(buffer, 0, this.buffsize);
                if (bufferReadResult > 0){
                    double frequency = get_pitch_from_short(buffer,this.frequency);
                    if (frequency > 50 && frequency < 500){
                        if (dataCallBack != null){
                            this.dataCallBack.data(frequency);
                        }
                        System.out.println("frequency:"+frequency);
                    }
                }
            }
            audioRecord.stop();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("错误");
        }
    }

}
