package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public synchronized void move(MoveType moveType) {
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

    private int replaseIndex(int indexOld, int indexNew, int[][] mas) {
        int result = 0;
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                if (mas[i][j] == indexOld) {
                    result++;
                    mas[i][j] = indexNew;
                }
            }
        }
        return result;
    }

    public int clearMatchPuyo() {

        int result = 0;
        int index = 1;
        int[][] indexArray = new int[boardWidth][boardHeight];
        Map<Integer, Integer> mapMatch = new HashMap<>();

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (boardMatrix[i][j].getPuyoType() == null) continue;
                if (((i + 1) != boardWidth) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i + 1][j].getPuyoType())) {
                    if ((indexArray[i][j] == 0) && ((indexArray[i + 1][j] == 0))) {
                        if ((j + 1 != boardHeight) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i][j + 1].getPuyoType())) {
                            if (indexArray[i][j + 1] == 0) {
                                indexArray[i][j + 1] = indexArray[i + 1][j] = indexArray[i][j] = index;
                                mapMatch.put(index, 3);
                                index++;
                            } else {
                                indexArray[i + 1][j] = indexArray[i][j] = indexArray[i][j + 1];
                                mapMatch.put(indexArray[i][j + 1], mapMatch.get(indexArray[i][j + 1]) + 2);

                            }
                        } else {
                            indexArray[i + 1][j] = indexArray[i][j] = index;
                            mapMatch.put(index, 2);
                            index++;
                        }
                    } else {
                        if (indexArray[i][j] == 0) {
                            indexArray[i][j] = indexArray[i + 1][j];
                            mapMatch.put(indexArray[i + 1][j], mapMatch.get(indexArray[i + 1][j]) + 1);
                        }
                        if (indexArray[i + 1][j] == 0) {
                            indexArray[i + 1][j] = indexArray[i][j];
                            mapMatch.put(indexArray[i][j], mapMatch.get(indexArray[i][j]) + 1);
                        }
                        if (indexArray[i + 1][j] != indexArray[i][j]) {
                            mapMatch.remove(indexArray[i + 1][j]);
                            mapMatch.put(indexArray[i][j], mapMatch.get(indexArray[i][j]) + replaseIndex(indexArray[i + 1][j], indexArray[i][j], indexArray));
                        }
                    }
                }
                //-----------------------
                if ((j + 1 != boardHeight) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i][j + 1].getPuyoType())) {
                    if ((indexArray[i][j] == 0) && ((indexArray[i][j + 1] == 0))) {
                        if ((i + 1 != boardWidth) && (boardMatrix[i][j].getPuyoType() == boardMatrix[i + 1][j].getPuyoType())) {
                            if (indexArray[i + 1][j] == 0) {
                                indexArray[i + 1][j] = indexArray[i + 1][j] = indexArray[i][j] = index;
                                mapMatch.put(index, 3);
                                index++;
                            } else {
                                indexArray[i][j + 1] = indexArray[i][j] = indexArray[i + 1][j];
                                mapMatch.put(indexArray[i + 1][j], mapMatch.get(indexArray[i + 1][j]) + 2);

                            }
                        } else {
                            indexArray[i][j + 1] = indexArray[i][j] = index;
                            mapMatch.put(index, 2);
                            index++;
                        }
                    } else {
                        if (indexArray[i][j] == 0) {
                            indexArray[i][j] = indexArray[i][j + 1];
                            mapMatch.put(indexArray[i][j + 1], mapMatch.get(indexArray[i][j + 1]) + 1);
                        }
                        if (indexArray[i][j + 1] == 0) {
                            indexArray[i][j + 1] = indexArray[i][j];
                            mapMatch.put(indexArray[i][j], mapMatch.get(indexArray[i][j]) + 1);
                        }
                        if (indexArray[i][j + 1] != indexArray[i][j]) {
                            mapMatch.remove(indexArray[i][j + 1]);
                            mapMatch.put(indexArray[i][j], mapMatch.get(indexArray[i][j]) + replaseIndex(indexArray[i][j + 1], indexArray[i][j], indexArray));


                        }
                    }
                }
                //-------------------------
            }
        }


        //calculate scope
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if ((indexArray[i][j] != 0) && (mapMatch.get(indexArray[i][j]) >= COUNT_MATCH_PUYO)) {
                    result++;
                    boardMatrix[i][j].setPuyoType(null);
                }
            }
        }

        return result;
    }

}
