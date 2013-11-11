package com.company;

public class App {
    public static void main(String[] args) {
        Game game = new Game();
        new Window(game.board.getBoardWidth() * Puyo.getSize() + 8 + Puyo.getSize() * 3, game.board.getBoardHeight() * Puyo.getSize() + 28, "Puyo-Puyo",game);
        new Thread(game).start();

    }
}
