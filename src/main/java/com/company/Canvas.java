package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Canvas extends JPanel implements Runnable {
    private final int DELAY = 25;
    private Thread animator;


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
        App.game.paint((Graphics2D) g, this);
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
