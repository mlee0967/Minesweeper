package com.github.mlee0967.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mlee0967.minesweeper.game.views.Board;
import com.github.mlee0967.minesweeper.game.Difficulty;
import com.github.mlee0967.minesweeper.game.Game;
import com.github.mlee0967.minesweeper.utils.Timer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        difficulty = Difficulty.BEGINNER;
        minesLeftView = (TextView) findViewById(R.id.MinesLeft);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame();
            }
        });
        timerView = (TextView) findViewById(R.id.Timer);
        boardView = (Board) findViewById(R.id.board);
        settingButton = (Button) findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDifficultySettingDialog();
            }
        });

        Game.getInstance().initGame(this, minesLeftView);
        startGame();
    }

    private void startGame(){
        if(timer!=null)
            timer.cancel();
        timer = new Timer(timerView);
        timer.start();
        Game.getInstance().startGame(difficulty, timer);
        boardView.setup();
    }

    private void showDifficultySettingDialog(){
        final String[] listItems = getResources().getStringArray(R.array.difficulty);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose difficulty");

        builder.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                switch(idx){
                    case 0:
                        difficulty = Difficulty.BEGINNER;
                        break;
                    case 1:
                        difficulty = Difficulty.INTERMEDIATE;
                        break;
                    case 2:
                        difficulty = Difficulty.EXPERT;
                }
                startGame();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private TextView minesLeftView;
    private TextView timerView;
    private Button resetButton;
    private Button settingButton;
    private Board boardView;
    private Timer timer;
    private Difficulty difficulty;
}
