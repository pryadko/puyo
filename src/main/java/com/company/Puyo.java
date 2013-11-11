package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Puyo {


    private static final int SIZE = 32;
    private static final Image PUYO_BLUE = new ImageIcon(Puyo.class.getResource("/puyo_blue.png")).getImage();
    private static final Image PUYO_GREEN = new ImageIcon(Puyo.class.getResource("/puyo_green.png")).getImage();
    private static final Image PUYO_RED = new ImageIcon(Puyo.class.getResource("/puyo_red.png")).getImage();
    private static final Image PUYO_YELLOW = new ImageIcon(Puyo.class.getResource("/puyo_yellow.png")).getImage();
    private PuyoType puyoType;

    public Puyo() {
        this(PuyoType.values()[new Random().nextInt(PuyoType.values().length)]);
    }

    public static int getSize() {
        return SIZE;
    }

    public Puyo(PuyoType puyoType) {
        this.puyoType = puyoType;
    }

    public void setPuyoType(PuyoType puyoType) {
        this.puyoType = puyoType;
    }

    public PuyoType getPuyoType() {
        return puyoType;
    }

    Image getImagePuyo() {
        if (puyoType == null) return null;
        switch (puyoType) {
            case BLUE:
                return PUYO_BLUE;
            case GREEN:
                return PUYO_GREEN;
            case RED:
                return PUYO_RED;
            case YELLOW:
                return PUYO_YELLOW;
            default:
                return null;
        }

    }

}
