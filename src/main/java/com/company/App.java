package com.company;

public class App {
    private static final int FILED_WIDTH = 6;
    private static final int FILED_HEIGHT = 12;

    public static void main(String[] args) {
        int filedWidth=FILED_WIDTH ;
        int filedHeight=FILED_HEIGHT ;
        try {
            if ((Integer.valueOf(args[0]) > 2)&&(Integer.valueOf(args[0]) < 20)) filedWidth = Integer.parseInt(args[0]);
            if ((Integer.valueOf(args[1]) > 4)&&(Integer.valueOf(args[1]) < 20)) filedHeight = Integer.parseInt(args[1]);
        } catch (Exception e){
             filedWidth = FILED_WIDTH;
             filedHeight = FILED_HEIGHT;
        }
        Game game = new Game(filedWidth,filedHeight);
        new Window(game.board.getBoardWidth() * Puyo.getSize() + 8 + Puyo.getSize() * 3, game.board.getBoardHeight() * Puyo.getSize() + 28, "Puyo-Puyo", game);
        new Thread(game).start();

    }
}
