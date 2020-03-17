package com.github.mlee0967.minesweeper.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer {
    public Timer(TextView timerView) {
        super(MAX_TIME, INTERVAL_MS);
        this.duration = MAX_TIME;
        this.timerView = timerView;
    }

    @Override
    public void onTick(long msUntilFinished) {
        time = (int) ((duration - msUntilFinished) / 1000);
        timerView.setText(String.format("%03d", time));
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }

    public int getTime(){
        return time;
    }

    private static final long INTERVAL_MS = 1000;
    private static final long MAX_TIME = 1000000;
    private final long duration;
    private int time;
    private TextView timerView;
}

