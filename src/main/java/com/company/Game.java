package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
enum MoveType {
    DOWN, LEFT, RIGHT,ROTATE
}
public class Game implements Runnable {
    private static final int MAX_FALLING_SPEED = 10;
    private static final int TIME_LEVEL_SPEED = 50;
    private static final int DELAY = 600;
    private static int maxSpeedDown = 1;
    private static final int SPEED_UP_PER_SCOPE = 10;
    private static final int SPEED_DEFAULT = 1;
    private boolean pauseGame;
    private boolean gameOver;
    private int sync;

    Board board;
    PairPuyo pairPuyo;


    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }


    private int scope;
    private int fallingSpeed;

    public Game(int boardWidth, int boardHeight) {

        pairPuyo = new PairPuyo();
        board = new Board(boardWidth,  boardHeight);
        pauseGame = false;
        gameOver = false;
        fallingSpeed = SPEED_DEFAULT;
        scope = 0;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_N) {
            if (gameOver) {
                gameOver = false;
                pauseGame = false;
                board.clear();
            }

        }
        if (key == KeyEvent.VK_ESCAPE) {
            if (!gameOver)
                pauseGame = !pauseGame;

        }

        if (key == KeyEvent.VK_LEFT) {
            this.board.move(MoveType.LEFT);
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.board.move(MoveType.RIGHT);
        }

        if (key == KeyEvent.VK_UP) {
            this.board.move(MoveType.ROTATE);
        }

        if (key == KeyEvent.VK_DOWN) {
            maxSpeedDown = MAX_FALLING_SPEED - fallingSpeed;
        }
    }

    public void paint(Graphics2D g2d, JPanel panel) {
        sync++;
        for (int i = 0; i < board.getBoardWidth(); i++)
            for (int j = 0; j < board.getBoardHeight(); j++) {
                g2d.drawImage(board.getBoardMatrix()[i][j].getImagePuyo(), i * Puyo.getSize(), j * Puyo.getSize(),panel);

            }

        g2d.drawImage(pairPuyo.getPairPuyo().get(1).getImagePuyo(), board.getBoardWidth() * Puyo.getSize(), 0, panel);
        g2d.drawImage(pairPuyo.getPairPuyo().get(0).getImagePuyo(), board.getBoardWidth() * Puyo.getSize(), Puyo.getSize(), panel);

        g2d.setColor(Color.black);
        g2d.drawLine(Puyo.getSize() * board.getBoardWidth(), 0, Puyo.getSize() * board.getBoardWidth(), Puyo.getSize() * board.getBoardHeight());

        g2d.drawString("SCOPE: " + getScope(), Puyo.getSize() * board.getBoardWidth() + 5, Puyo.getSize() * 3);
        g2d.drawString("SPEED: " + fallingSpeed, Puyo.getSize() * board.getBoardWidth() + 5, Puyo.getSize() * 3 + 20);
        g2d.setColor(Color.RED);
        g2d.drawString("ESC - pause", Puyo.getSize() * board.getBoardWidth() + 5, Puyo.getSize() * 3 + 40);
        g2d.drawString("N - new game", Puyo.getSize() * board.getBoardWidth() + 5, Puyo.getSize() * 3 + 60);
        if (gameOver) {

            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.setColor(Color.WHITE);
            g2d.fillRect(panel.getWidth() / 2 - "GAME OVER".length() * 8, panel.getHeight() / 2 - 17, 120, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString("GAME OVER", panel.getWidth() / 2 - "GAME OVER".length() * 8, panel.getHeight() / 2);
        }
        if (pauseGame) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            if (sync > 10)
                g2d.drawString("PAUSE", panel.getWidth() / 2 - "PAUSE".length() * 8, panel.getHeight() / 2);
            if (sync > 20) sync = 0;
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

            this.board.move(MoveType.DOWN);
            if (!this.board.isMove()) {
                if (!this.board.moveSingelton()) {

                    int countClearMatchPuyo = board.clearMatchPuyo();
                    scope += countClearMatchPuyo;
                    fallingSpeed = scope / SPEED_UP_PER_SCOPE + SPEED_DEFAULT;


                    if (!this.board.moveSingelton() && (this.board.clearMatchPuyo() == 0) && !gameOver) {
                        gameOver = !this.board.setNewPairPayo(pairPuyo.getPairPuyo(), this.board.getBoardWidth() / 2, 0);
                        pairPuyo = new PairPuyo();
                    }
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

