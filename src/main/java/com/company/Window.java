package com.company;

import javax.swing.*;

public class Window extends JFrame {

    static private final int WINDOW_WIDTH = App.game.board.getBoardWidth() * Puyo.SIZE+8+Puyo.SIZE*3;
    static private final int WINDOW_HEIGHT = App.game.board.getBoardHeight() * Puyo.SIZE+28;
    static private final String TITLE_WINDOW = "Puyo-Puyo";

    public Window() {
        add(new Canvas());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE_WINDOW);
        setResizable(false);
        setVisible(true);

    }
}
