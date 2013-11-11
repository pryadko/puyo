package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Canvas extends JPanel implements Runnable {
    private final int DELAY = 25;
    private Thread animator;
    private Game game;

    public Canvas(Game game) {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        this.game = game;
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
        // paint board and other
        game.paint((Graphics2D) g, this);
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
    // keyboard listener
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e);
        }
    }
}
