package com.company;

import java.util.List;

public class Board {
//    public void setBoardWidth(int boardWidth) {
//        this.boardWidth = boardWidth;
//    }
//
//    public void setBoardHeight(int boardHeight) {
//        this.boardHeight = boardHeight;
//    }

    static final int FILED_WIDTH = 6;
    static final int FILED_HEIGHT = 12;
    static final int COUNT_MATCH_PUYO = 4;
    private int boardWidth = FILED_WIDTH;
    private int boardHeight = FILED_HEIGHT;
    private boolean move;

    private Puyo[][] boardMatrix;
    private int puyoFirstX, puyoFirstY, puyoSecondX, puyoSecondY;


    public int getBoardWidth() {
        return boardWidth;
    }

    public Puyo[][] getBoardMatrix() {
        return boardMatrix;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public Board() {
        boardMatrix = new Puyo[boardWidth][boardHeight];
        for (int i = 0; i < boardWidth; i++)
            for (int j = 0; j < boardHeight; j++)
                boardMatrix[i][j] = new Puyo(null);
        move = false;
    }

    public boolean setNewPairPayo(List<Puyo> pairPuyo, int startX, int startY) {
        if ((boardMatrix[startX][startY].getPuyoType() == null) && (boardMatrix[startX][startY + 1].getPuyoType() == null)) {
            boardMatrix[startX][startY].setPuyoType(pairPuyo.get(1).getPuyoType());
            boardMatrix[startX][startY + 1].setPuyoType(pairPuyo.get(0).getPuyoType());
            puyoFirstX = startX;
            puyoFirstY = startY + 1;
            puyoSecondX = startX;
            puyoSecondY = startY;

            move = true;

            return true;
        } else {
            move = false;
            return false;

        }
    }


    public boolean isMove() {
        return move;
    }

    public void move() {

        if (move) {
            if ((puyoFirstY != boardHeight - 1) && (boardMatrix[puyoFirstX][puyoFirstY + 1].getPuyoType() == null) &&
                    ((puyoFirstX == puyoSecondX) || (boardMatrix[puyoSecondX][puyoSecondY + 1].getPuyoType() == null))) {
                PuyoType temp;
                temp = boardMatrix[puyoFirstX][puyoFirstY].getPuyoType();
                boardMatrix[puyoFirstX][puyoFirstY] = new Puyo(null);
                boardMatrix[puyoFirstX][++puyoFirstY].setPuyoType(temp);

                temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                boardMatrix[puyoSecondX][++puyoSecondY].setPuyoType(temp);
            } else move = false;
        }
    }

    public void moveLeft() {
        if (move) {
            if ((puyoFirstX != 0) && (boardMatrix[puyoFirstX - 1][puyoFirstY].getPuyoType() == null)) {
                PuyoType temp;
                temp = boardMatrix[puyoFirstX][puyoFirstY].getPuyoType();
                boardMatrix[puyoFirstX][puyoFirstY] = new Puyo(null);
                boardMatrix[--puyoFirstX][puyoFirstY].setPuyoType(temp);

                temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                boardMatrix[--puyoSecondX][puyoSecondY].setPuyoType(temp);
            }
        }

    }

    public void moveRight() {
        if (move) {
            if ((puyoSecondX != boardWidth - 1) && (boardMatrix[puyoSecondX + 1][puyoSecondY].getPuyoType() == null) &&
                    ((puyoFirstX != puyoSecondX) || (boardMatrix[puyoFirstX + 1][puyoFirstY].getPuyoType() == null))) {
                PuyoType temp;
                temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                boardMatrix[++puyoSecondX][puyoSecondY].setPuyoType(temp);

                temp = boardMatrix[puyoFirstX][puyoFirstY].getPuyoType();
                boardMatrix[puyoFirstX][puyoFirstY] = new Puyo(null);
                boardMatrix[++puyoFirstX][puyoFirstY].setPuyoType(temp);


            }
        }

    }

    public void moveRotate() {
        if (move) {
            // if((puyoSecondX != boardWidth-1) && (boardMatrix[puyoSecondX+1][puyoSecondY ].getPuyoType()== null)&&
            //         ((puyoFirstX==puyoSecondX)||(boardMatrix[puyoSecondX+1][puyoSecondY ].getPuyoType()== null))){
            if (puyoFirstX == puyoSecondX) {
                if ((puyoSecondX != boardWidth - 1) && (boardMatrix[puyoSecondX + 1][puyoSecondY + 1].getPuyoType() == null)) {
                    PuyoType temp;
                    temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                    boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                    boardMatrix[++puyoSecondX][++puyoSecondY].setPuyoType(temp);
                }

            } else {
                PuyoType temp;
                temp = boardMatrix[puyoFirstX][puyoFirstY].getPuyoType();
                boardMatrix[puyoFirstX][puyoFirstY] = new Puyo(null);
                boardMatrix[puyoFirstX][--puyoFirstY].setPuyoType(temp);

                temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                boardMatrix[--puyoSecondX][puyoSecondY].setPuyoType(temp);
                int tempInt = puyoFirstY;
                puyoFirstY = puyoSecondY;
                puyoSecondY = tempInt;
            }

            //     }
        }
    }

    boolean moveSingelton() {
        boolean result = false;

        for (int i = 0; i < boardWidth; i++) {
            for (int j = boardHeight - 1; j > 0; j--) {
                if ((boardMatrix[i][j].getPuyoType() == null) && (boardMatrix[i][j - 1].getPuyoType() != null)) {

                    boardMatrix[i][j].setPuyoType(boardMatrix[i][j - 1].getPuyoType());
                    boardMatrix[i][j - 1].setPuyoType(null);
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean clearMatchPuyo() {

        boolean result = false;
        Puyo[][] tempBoardMatrix = new Puyo[boardWidth][boardHeight];
        PuyoType tempPuyo;
        int startIndex ;
        int countEqualsPuyo;
        //vertical clean
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight - 1; j++) {
                if ((boardMatrix[i][j].getPuyoType() == boardMatrix[i][j + 1].getPuyoType()) && (boardMatrix[i][j].getPuyoType() != null)) {
                    tempPuyo = boardMatrix[i][j].getPuyoType();
                    startIndex = j;
                    countEqualsPuyo = 2;
                    while ((j+countEqualsPuyo < boardHeight) && (tempPuyo == boardMatrix[i][j+countEqualsPuyo].getPuyoType())) {
                        countEqualsPuyo++;
                    }
                    if (countEqualsPuyo >= COUNT_MATCH_PUYO) {
                        for (int t = startIndex; t < startIndex + countEqualsPuyo; t++) {
                            tempBoardMatrix[i][t] = new Puyo(tempPuyo);
                        }
                    }
                    j = startIndex + countEqualsPuyo-1;
                }
            }

        }
        //gorizont clean match
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth - 1; i++) {
                if ((boardMatrix[i][j].getPuyoType() != null) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i + 1][j].getPuyoType())) {
                    tempPuyo = boardMatrix[i][j].getPuyoType();
                    startIndex = i;
                    countEqualsPuyo = 2;
                    while ((i+countEqualsPuyo < boardWidth) && (tempPuyo == boardMatrix[i+countEqualsPuyo][j].getPuyoType())) {
                        countEqualsPuyo++;
                    }
                    if (countEqualsPuyo >= COUNT_MATCH_PUYO) {
                        for (int t = startIndex; t < i+countEqualsPuyo; t++) {
                            tempBoardMatrix[t][j] = new Puyo(tempPuyo);
                        }
                    }
                    i=startIndex+countEqualsPuyo-1;
                }
            }
        }
        int scopeTemp = 0;
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (tempBoardMatrix[i][j] != null) {
                    result = true;
                    boardMatrix[i][j].setPuyoType(null);
                    scopeTemp++;
                }
            }
        }
        if (result) {
            App.game.setScope(scopeTemp + App.game.getScope());
            App.game.setFallingSpeed(App.game.getScope() / 10 + 1);
        }
        return result;
    }
}
