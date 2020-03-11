package com.github.mlee0967.minesweeper;


import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;
    private final long duration;
    private TextView timerView;

    protected Timer(TextView timerView) {
        super(Long.MAX_VALUE, INTERVAL_MS);
        this.duration = Long.MAX_VALUE;
        this.timerView = timerView;
    }

    public void onTick(int second){
        timerView.setText(String.valueOf(second));
    }

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}

