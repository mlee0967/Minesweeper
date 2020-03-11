package com.github.mlee0967.minesweeper.game.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class Board extends GridView {
    public Board(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}