package com.github.mlee0967.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mlee0967.minesweeper.game.views.BoardAdapter;
import com.github.mlee0967.minesweeper.game.views.Board;
import com.github.mlee0967.minesweeper.game.Difficulty;
import com.github.mlee0967.minesweeper.game.Game;
import com.github.mlee0967.minesweeper.utils.Timer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minesLeftView = (TextView) findViewById(R.id.MinesLeft);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame();
            }
        });
        timerView = (TextView) findViewById(R.id.Timer);
        boardView = (Board) findViewById(R.id.board);

        Game.getInstance().initGame(this, minesLeftView);
        startGame();
    }

    private void startGame(){
        Timer timer = new Timer(timerView);
        timer.start();
        Game.getInstance().startGame(Difficulty.BEGINNER, timer);
        boardView.setNumColumns(Game.getInstance().getWidth());
        boardView.setAdapter(new BoardAdapter());
    }

    public TextView minesLeftView;
    public TextView timerView;
    public Button resetButton;
    private Board boardView;
}
