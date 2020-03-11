package com.github.mlee0967.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.github.mlee0967.minesweeper.game.BoardAdapter;
import com.github.mlee0967.minesweeper.game.BoardView;
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

        Game.getInstance().start(this);
        boardView = (BoardView) findViewById(R.id.board);
        boardView.setNumColumns(Game.getInstance().getWidth());
        boardView.setAdapter(new BoardAdapter());
    }

    private TextView minesLeftView;
    private TextView timerView;
    private BoardView boardView;
}
