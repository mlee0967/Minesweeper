package com.github.mlee0967.minesweeper.game.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.github.mlee0967.minesweeper.game.Difficulty;
import com.github.mlee0967.minesweeper.game.Game;

public class Board extends GridView {
    public Board(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setup(){
        this.setNumColumns(Game.getInstance().getWidth());
        setMargin(Game.getInstance().getDifficulty());
        this.setAdapter(new BoardAdapter());
    }

    public void setMargin(Difficulty difficulty){
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        switch(difficulty){
            case BEGINNER:
            case INTERMEDIATE:
                layoutParams.topMargin=300;
                break;
            case EXPERT:
                layoutParams.topMargin=60;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class BoardAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Game.getInstance().getWidth() * Game.getInstance().getHeight();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return Game.getInstance().getCell(position);
        }

    }
}