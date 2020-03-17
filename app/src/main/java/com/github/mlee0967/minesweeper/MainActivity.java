package com.github.mlee0967.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mlee0967.minesweeper.game.views.Board;
import com.github.mlee0967.minesweeper.game.Difficulty;
import com.github.mlee0967.minesweeper.game.Game;
import com.github.mlee0967.minesweeper.utils.Timer;

import java.util.ArrayList;
import java.util.List;

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
        recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRecordDialog();
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
        List<String> difficulties = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values())
            difficulties.add(difficulty.toString());
        final String[] listItems = difficulties.toArray(new String[difficulties.size()]);
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
        builder.create().show();
    }

    private void showRecordDialog(){
        SharedPreferences prefs = getSharedPreferences(
                getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        StringBuilder sb = new StringBuilder();
        for (Difficulty difficulty : Difficulty.values()){
            sb.append(difficulty.toString());
            sb.append(" : ");
            int record = prefs.getInt(difficulty.toString(), -1);
            if(record==-1)
                sb.append("---");
            else
                sb.append(record);
            sb.append("\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Records");
        builder.setMessage(sb.toString());
        builder.setCancelable(true);
        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private TextView minesLeftView;
    private TextView timerView;
    private Button recordButton;
    private Button resetButton;
    private Button settingButton;
    private Board boardView;
    private Timer timer;
    private Difficulty difficulty;
}
