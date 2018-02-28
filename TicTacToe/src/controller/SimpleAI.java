package controller;

import model.GameBoard;
import model.Stone;

import static java.util.concurrent.ThreadLocalRandom.current;

public class SimpleAI {
    int counter = 1;

    public SimpleAI() {
    }

    //get the state of the gameboard
    protected void updateGameBoard(GameBoard model) {
        alwaysDraw(model);
        counter++;
    }

    /**
     * Adds stones randomly on the field. Easiest difficulty.
     *
     * @param model model of the current game board
     */
    private void addRandomStone(GameBoard model) {
        int row = getRandomNumber();
        int col = getRandomNumber();
        while (model.getStone(row, col) != Stone.NONE) {
            row = getRandomNumber();
            col = getRandomNumber();
        }
        model.setStone(row, col, Stone.O);
    }

    /**
     * Adds stones in a way to the board, that should always lead to a draw
     *
     * @param model model of the current game board
     */
    private void alwaysDraw(GameBoard model) {
        //if there is no stone set in the middle, set a stone in the middle
        if (counter == 1) {
            if (model.getStone(1, 1) == (Stone.NONE) && counter == 1) {
                model.setStone(1, 1, Stone.O);
            } else {
                //if there is a stone in the middle, set the stone in one of the edges
                model.setStone(getRandomEvenNumber(), getRandomEvenNumber(), Stone.O);
            }
        } else {
            if (!checkDiagonal(model)) {
                if (!checkRows(model)) {
                    if (!checkCols(model)) {
                        if (!checkCorners(model)) {
                            checkStraights(model);
                        }
                    }
                }
            }
        }
    }

    private void checkStraights(GameBoard model) {
        int r = getRandomNumber();
        int c = getRandomNumber();
        if(model.getStone(r, c) == Stone.NONE && (r + c > 0 && r + c < 4)) {
            model.setStone(r, c, Stone.O);
        }else{
            checkStraights(model);
        }

    }

    private boolean checkCorners(GameBoard model) {
        int cornerCount = 0;
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 2; c++) {
                if (model.getStone(r * 2, c * 2) == Stone.X) {
                    cornerCount++;
                    if (cornerCount < 2 && model.getStone(r * 2, c * 2) == Stone.NONE) {
                        model.setStone(r * 2, c * 2, Stone.O);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(GameBoard model) {
        if (model.getStone(1, 1) != Stone.X) return false;
        if (model.getStone(1, 1) == Stone.X &&
                model.getStone(0, 0) == Stone.X &&
                model.getStone(2, 2) != Stone.O) {
            model.setStone(2, 2, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(0, 2) == Stone.X &&
                model.getStone(2, 0) != Stone.O) {
            model.setStone(2, 0, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(2, 0) == Stone.X &&
                model.getStone(0, 2) != Stone.O) {
            model.setStone(0, 2, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(2, 2) == Stone.X &&
                model.getStone(0, 0) != Stone.O) {
            model.setStone(0, 0, Stone.O);
            return true;
        }
        return false;
    }

    private boolean checkRows(GameBoard model) {
        for (int r = 0; r < 3; r++) {
            int stoneCount = 0;
            for (int c = 0; c < 3; c++) {
                if (model.getStone(r, c) == Stone.X) {
                    stoneCount++;
                }else if(model.getStone(r,c) == Stone.O){
                    stoneCount--;
                }
            }
            if (stoneCount == 2) {
                counterMoveRow(model, r);
                return true;
            }
        }
        return false;
    }

    private boolean checkCols(GameBoard model) {
        for (int c = 0; c < 3; c++) {
            int stoneCount = 0;
            for (int r = 0; r < 3; r++) {
                if (model.getStone(r, c) == Stone.X) {
                    stoneCount++;
                }else if(model.getStone(r,c) == Stone.O) stoneCount--;
            }
            if (stoneCount == 2) {
                counterMoveCol(model, c);
                return true;
            }
        }
        return false;
    }

    private void counterMoveCol(GameBoard model, int c) {
        for (int r = 0; r < 3; r++) {
            if (model.getStone(r, c) == Stone.NONE) model.setStone(r, c, Stone.O);
        }
    }

    private void counterMoveRow(GameBoard model, int r) {
        for (int c = 0; c < 3; c++) {
            if (model.getStone(r, c) == Stone.NONE) model.setStone(r, c, Stone.O);
        }
    }

    private int getRandomNumber() {
        return current().nextInt(0, 3);
    }

    private int getRandomEvenNumber() {
        return current().nextInt(0, 2) * 2;
    }

}
