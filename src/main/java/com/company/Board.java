package com.company;

import java.util.List;

public class Board {


    private static final int COUNT_MATCH_PUYO = 4;
    private int boardWidth;
    private int boardHeight;
    private boolean move;

    private Puyo[][] boardMatrix;
    private int puyoFirstX, puyoFirstY, puyoSecondX, puyoSecondY;


    public int getBoardWidth() {
        return boardWidth;
    }

    public Puyo[][] getBoardMatrix() {
        return boardMatrix;
    }

    public void setBoardMatrix(Puyo[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        clear();
    }
    // clear board
    public void clear() {
        boardMatrix = new Puyo[boardWidth][boardHeight];
        for (int i = 0; i < boardWidth; i++)
            for (int j = 0; j < boardHeight; j++)
                boardMatrix[i][j] = new Puyo(null);
        move = false;
    }
    //set new pair payo  on board if return false then game over
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

    //move pair payo on board such us LEFT,RIGHT,DOWN and ROTATE
    public  synchronized void move(MoveType moveType) {
                if (move) {
                switch (moveType) {
                    case DOWN: {
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
                        break;
                    }
                    case LEFT: {
                        if ((puyoFirstX != 0) && (boardMatrix[puyoFirstX - 1][puyoFirstY].getPuyoType() == null)) {
                            PuyoType temp;
                            temp = boardMatrix[puyoFirstX][puyoFirstY].getPuyoType();
                            boardMatrix[puyoFirstX][puyoFirstY] = new Puyo(null);
                            boardMatrix[--puyoFirstX][puyoFirstY].setPuyoType(temp);

                            temp = boardMatrix[puyoSecondX][puyoSecondY].getPuyoType();
                            boardMatrix[puyoSecondX][puyoSecondY] = new Puyo(null);
                            boardMatrix[--puyoSecondX][puyoSecondY].setPuyoType(temp);
                        }
                        break;
                    }
                    case RIGHT: {
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
                        break;
                    }
                    case ROTATE: {
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
                    }
                    break;
                }
            }
        }


    // delete empty cells
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
    //find vertical or horizontal equals puyo
    public int clearMatchPuyo() {

        int result = 0;
        Puyo[][] tempBoardMatrix = new Puyo[boardWidth][boardHeight];
        PuyoType tempPuyo;
        int startIndex;
        int countEqualsPuyo;
        //vertical clean
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight - 1; j++) {
                if ((boardMatrix[i][j].getPuyoType() == boardMatrix[i][j + 1].getPuyoType()) && (boardMatrix[i][j].getPuyoType() != null)) {
                    tempPuyo = boardMatrix[i][j].getPuyoType();
                    startIndex = j;
                    countEqualsPuyo = 2;
                    while ((j + countEqualsPuyo < boardHeight) && (tempPuyo == boardMatrix[i][j + countEqualsPuyo].getPuyoType())) {
                        countEqualsPuyo++;
                    }
                    if (countEqualsPuyo >= COUNT_MATCH_PUYO) {
                        for (int t = startIndex; t < startIndex + countEqualsPuyo; t++) {
                            tempBoardMatrix[i][t] = new Puyo(tempPuyo);
                        }
                    }
                    j = startIndex + countEqualsPuyo - 1;
                }
            }

        }
        //horizontal clean match
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth - 1; i++) {
                if ((boardMatrix[i][j].getPuyoType() != null) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i + 1][j].getPuyoType())) {
                    tempPuyo = boardMatrix[i][j].getPuyoType();
                    startIndex = i;
                    countEqualsPuyo = 2;
                    while ((i + countEqualsPuyo < boardWidth) && (tempPuyo == boardMatrix[i + countEqualsPuyo][j].getPuyoType())) {
                        countEqualsPuyo++;
                    }
                    if (countEqualsPuyo >= COUNT_MATCH_PUYO) {
                        for (int t = startIndex; t < i + countEqualsPuyo; t++) {
                            tempBoardMatrix[t][j] = new Puyo(tempPuyo);
                        }
                    }
                    i = startIndex + countEqualsPuyo - 1;
                }
            }
        }
        //calculate scope
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (tempBoardMatrix[i][j] != null) {
                    result++;
                    boardMatrix[i][j].setPuyoType(null);
                }
            }
        }

        return result;
    }
}
