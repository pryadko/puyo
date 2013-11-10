package com.company;

import java.awt.event.KeyEvent;

public class Game implements Runnable {
    static final int MAX_FALLING_SPEED = 10;
    static final int TIME_LEVEL_SPEED = 50;
    static final int DELAY = 600;
    static int maxSpeedDown = 1;
    boolean pauseGame;
    boolean gameOver;

    Board board;
    PairPuyo pairPuyo;

    public int getFallingSpeed() {
        return fallingSpeed;
    }

    public void setFallingSpeed(int fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }


    private int scope;
    private int fallingSpeed;

    public Game() {
        pairPuyo = new PairPuyo();
        board = new Board();
        pauseGame = false;
        gameOver = false;
        fallingSpeed = 1;
        scope = 0;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_N) {
            if (gameOver) {
                gameOver = false;
                pauseGame = false;
                board = new Board();
            }

        }
        if (key == KeyEvent.VK_ESCAPE) {
            pauseGame = !pauseGame;

        }

        if (key == KeyEvent.VK_LEFT) {
            this.board.moveLeft();
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.board.moveRight();
        }

        if (key == KeyEvent.VK_UP) {
            this.board.moveRotate();
        }

        if (key == KeyEvent.VK_DOWN) {
            maxSpeedDown = MAX_FALLING_SPEED - fallingSpeed;

        }
    }


    @Override
    public void run() {
        long sleep;


        while (true) {

            if (gameOver)
                Thread.yield();
            else while (pauseGame)
                Thread.yield();

            this.board.move();
            if (!this.board.isMove()) {
                if (!this.board.moveSingelton() && !this.board.clearMatchPuyo() && !gameOver) {
                    gameOver = !this.board.setNewPairPayo(pairPuyo.getPairPuyo(), this.board.getBoardWidth() / 2, 0);
                    pairPuyo = new PairPuyo();
                }
            }
            sleep = DELAY - (fallingSpeed + maxSpeedDown) * TIME_LEVEL_SPEED;
            maxSpeedDown = 0;
            try {
                Thread.sleep(sleep);

            } catch (InterruptedException e) {
                System.out.println("interrupted error");
                e.printStackTrace();
            }
        }

    }
}

