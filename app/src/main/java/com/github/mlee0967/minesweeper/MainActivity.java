package com.github.mlee0967.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mlee0967.minesweeper.game.BoardAdapter;
import com.github.mlee0967.minesweeper.game.Board;
import com.github.mlee0967.minesweeper.game.Game;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minesLeftView = (TextView) findViewById(R.id.MinesLeft);
        timerView = (TextView) findViewById(R.id.Timer);

        Timer timer = new Timer(timerView);
        timer.start();

        Game.getInstance().start(this, minesLeftView, timer);
        board = (Board) findViewById(R.id.board);
        board.setNumColumns(Game.getInstance().getWidth());
        board.setAdapter(new BoardAdapter());
    }

    public TextView minesLeftView;
    public TextView timerView;
    private Board board;
}
