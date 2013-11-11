package com.company;

import javax.swing.*;

public class Window extends JFrame {

    public Window(int windowWidth, int windowHeight, String windowTitle) {
        add(new Canvas());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
        setTitle(windowTitle);
        setResizable(false);
        setVisible(true);

    }
}
