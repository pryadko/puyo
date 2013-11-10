package com.company;

public class App
{
    static Game game = new Game();
    public static void main(String[] args) {
        new Window();
        new Thread(game).start();
    }
}
