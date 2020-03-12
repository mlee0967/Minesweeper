package com.github.mlee0967.minesweeper.game;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mlee0967.minesweeper.utils.Timer;
import com.github.mlee0967.minesweeper.game.views.Cell;

import java.util.Random;

public class Game {
    static public Game getInstance(){
        if(instance == null)
            instance = new Game();

        return instance;
    }

    private Game(){}

    public void initBoard(){
        board = new Cell[height][width];
        for(int i=0; i<height; ++i) {
            board[i] = new Cell[width];
            for(int j=0; j<width; ++j){
                board[i][j] = new Cell(context, i, j);
                board[i][j].invalidate();
            }
        }
    }

    public void initBoardSettings(Difficulty difficulty){
        this.difficulty = difficulty;
        switch(difficulty){
            case BEGINNER:
                width = 9;
                height = 9;
                mines = 9;
                break;
            case INTERMEDIATE:
                width = 12;
                height = 12;
                mines = 30;
                break;
            case EXPERT:
                width = 12;
                height = 18;
                mines = 60;
        }
    }

    public void initGame(Context context, TextView minesLeftView){
        this.context = context;
        this.minesLeftView = minesLeftView;
    }

    public void startGame(Difficulty difficulty, Timer timer){
        initBoardSettings(difficulty);
        initBoard();
        setMinesLeft(mines);
        cellsLeft = width*height;
        gameOver = false;
        this.timer = timer;
    }

    public void click(int row, int col){
        if(board[row][col].isRevealed())
            return;

        //if first click (can happen after placing flag)
        if(cellsLeft==width*height-(mines-minesLeft)){
            setMines(row, col);
        }

        board[row][col].setClicked();
        board[row][col].setRevealed();
        if(board[row][col].isMine()){
            endGame(false);
        }else{
            reveal(row, col);
        }

        if(cellsLeft==0 && minesLeft==0)
            endGame(checkWin());
    }

    public void flag(int row, int col){
        if(board[row][col].isFlagged()){
            board[row][col].setFlagged(false);
            setMinesLeft(minesLeft+1);
            ++cellsLeft;

        }else if(!board[row][col].isRevealed()){
            board[row][col].setFlagged(true);
            setMinesLeft(minesLeft-1);
            --cellsLeft;
            if(cellsLeft==0 && minesLeft==0)
                endGame(checkWin());
        }
    }

    public Cell getCell(int position){
        int row = position / width;
        int col = position % width;
        return board[row][col];
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    private boolean checkWin(){
        for(int r=0; r<height; ++r){
            for(int c=0; c<width; ++c){
                if(!board[r][c].isMine() && board[r][c].isFlagged())
                    return false;
            }
        }
        return true;
    }

    private void endGame(boolean won){
        timer.cancel();
        gameOver = true;

        if(won)
            Toast.makeText(context,"Game Won", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context,"Game lost", Toast.LENGTH_SHORT).show();

        for(int r=0; r<height; ++r){
            for(int c=0; c<width; ++c){
                board[r][c].setRevealed();
            }
        }
    }

    private void reveal(int row, int col){
        --cellsLeft;
        if(board[row][col].getVal()==0) {
            for(int[] dir: dirs){
                int adj_row = row + dir[0];
                int adj_col = col + dir[1];
                if (adj_row>=0 && adj_row<height && adj_col>=0 && adj_col<width &&
                        !board[adj_row][adj_col].isRevealed() && !board[adj_row][adj_col].isMine()) {
                    board[adj_row][adj_col].setRevealed();
                    reveal(adj_row, adj_col);
                }
            }
        }
    }

    //first clicked cell should never be mine
    private void setMines(int clicked_row, int clicked_col){
        Random rand = new Random();
        int unplaced_bombs = mines;
        while(unplaced_bombs>0){
            int row = (rand.nextInt() & Integer.MAX_VALUE)%height;
            int col = (rand.nextInt() & Integer.MAX_VALUE)%width;
            if((row!=clicked_row || col!=clicked_col) && !board[row][col].isMine()){
                board[row][col].setMine();
                --unplaced_bombs;
                for(int[] dir: dirs){
                    int adj_row = row+dir[0];
                    int adj_col = col+dir[1];
                    if(adj_row>=0 && adj_row<height && adj_col>=0 && adj_col<width){
                        board[adj_row][adj_col].incVal();
                    }
                }
            }
        }
    }

    private void setMinesLeft(int num){
        minesLeft = num;
        minesLeftView.setText(String.format("%03d", minesLeft));
    }

    static private Game instance;
    private int width;
    private int height;
    private int mines;
    private int minesLeft;
    private TextView minesLeftView;
    private int cellsLeft;
    private Cell[][] board;
    private int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {-1,-1}, {1,-1}, {-1,1}};
    private boolean gameOver;
    private Context context;
    private Timer timer;
    private Difficulty difficulty;
}