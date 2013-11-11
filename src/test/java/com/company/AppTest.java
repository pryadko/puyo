package com.company;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest 

{

    @Test
    public void testClearMatchPuyo() {
        Game game = new Game(6,12);
        Puyo[][] puyos = new Puyo[game.board.getBoardWidth()][game.board.getBoardHeight()];
        for (int i=0;i<game.board.getBoardWidth();i++)
            for (int j=0;j<game.board.getBoardHeight();j++){
               puyos[i][j]= new Puyo(PuyoType.BLUE);
            }
        game.board.setBoardMatrix(puyos);
        game.board.clearMatchPuyo();
        for (int i=0;i<game.board.getBoardWidth();i++)
        for (int j=0;j<game.board.getBoardHeight();j++){

            assertEquals(null, puyos[i][j].getPuyoType());
        }

    }

}
