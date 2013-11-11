package com.company;

public class App {
    static Game game = new Game();

    public static void main(String[] args) {
        new Window(game.board.getBoardWidth() * Puyo.getSize() + 8 + Puyo.getSize() * 3, game.board.getBoardHeight() * Puyo.getSize() + 28, "Puyo-Puyo");
        new Thread(game).start();
    }
}
