package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Canvas extends JPanel implements Runnable {
    private final int DELAY = 50;
    private Thread animator;
    private Game game = App.game;
    private long sync;

    public Canvas() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        sync++;
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < game.board.getBoardWidth(); i++)
            for (int j = 0; j < game.board.getBoardHeight(); j++) {
                g2d.drawImage(game.board.getBoardMatrix()[i][j].getImagePuyo(), i * Puyo.SIZE, j * Puyo.SIZE, this);
            }

        g2d.drawImage(game.pairPuyo.getPairPuyo().get(1).getImagePuyo(), App.game.board.getBoardWidth() * Puyo.SIZE, 0, this);
        g2d.drawImage(game.pairPuyo.getPairPuyo().get(0).getImagePuyo(), App.game.board.getBoardWidth() * Puyo.SIZE, Puyo.SIZE, this);

        g2d.setColor(Color.black);
        g2d.drawLine(Puyo.SIZE * game.board.getBoardWidth(), 0, Puyo.SIZE * App.game.board.getBoardWidth(), Puyo.SIZE * App.game.board.getBoardHeight());

        g2d.drawString("SCOPE: "+game.getScope(),Puyo.SIZE * game.board.getBoardWidth()+5,Puyo.SIZE*3);
        g2d.drawString("SPEED: "+game.getFallingSpeed(),Puyo.SIZE * game.board.getBoardWidth()+5,Puyo.SIZE*3+20);
        g2d.setColor(Color.RED);
        g2d.drawString("ESC - pause",Puyo.SIZE * game.board.getBoardWidth()+5,Puyo.SIZE*3+40);
        g2d.drawString("N - new game",Puyo.SIZE * game.board.getBoardWidth()+5,Puyo.SIZE*3+60);
        if (game.gameOver) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("GAME OVER", this.getWidth() / 2-"GAME OVER".length()*8, this.getHeight() / 2);
        }
        if (game.pauseGame) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            if (sync > 10)
                g2d.drawString("PAUSE", this.getWidth() / 2-"PAUSE".length()*8, this.getHeight() / 2);
            if (sync > 20) sync = 0;
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println("interrupted error");
                e.printStackTrace();
            }
        }
    }

    private class TAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {
            App.game.keyPressed(e);
        }
    }
}
