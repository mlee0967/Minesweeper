package com.github.mlee0967.minesweeper.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BoardAdapter extends BaseAdapter {

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