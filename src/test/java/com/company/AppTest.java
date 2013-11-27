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

        Puyo[][] puyosEtalon = new Puyo[6][12];
        Puyo[][] puyosTesting = new Puyo[6][12];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 12; j++) {
                puyosEtalon[i][j].setPuyoType(new Puyo().getPuyoType());
                puyosTesting[i][j].setPuyoType(puyosEtalon[i][j].getPuyoType());
            }
        Game game = new Game(6, 12);
     //   game.board.setBoardMatrix(puyosEtalon);
   //     game.board.clearMatchPuyo();
       // game.board.setBoardMatrix(puyosTesting);
      //  game.board.clearMatchPuyo();
        for (int i = 0; i < game.board.getBoardWidth(); i++)
            for (int j = 0; j < game.board.getBoardHeight(); j++) {

                assertEquals(true,puyosEtalon[i][j].getPuyoType()== puyosTesting[i][j].getPuyoType());
            }

    }

}
